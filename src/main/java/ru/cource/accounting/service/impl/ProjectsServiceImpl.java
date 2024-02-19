package ru.cource.accounting.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cource.accounting.dto.models.GetProjectsResponse;
import ru.cource.accounting.dto.models.ProjectDto;
import ru.cource.accounting.models.Project;
import ru.cource.accounting.repository.ProjectRepository;
import ru.cource.accounting.service.ProjectsService;
import ru.cource.accounting.utils.DateFormatUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProjectsServiceImpl implements ProjectsService {

    private final ProjectRepository projectRepository;

    @Override
    public GetProjectsResponse getProjects(int page, int countOnPage) {
        List<Project> projects = projectRepository.loadListOfProjects(PageRequest.of(page, countOnPage, Sort.DEFAULT_DIRECTION, "id")).stream().toList();
        GetProjectsResponse response = new GetProjectsResponse();
        Stream<ProjectDto> projectDtoStream = projects.stream().map(ProjectDto::toDto);
        List<ProjectDto> collect = projectDtoStream.collect(Collectors.toList());
        collect.forEach(projectDto -> projectDto.setIncome(projectRepository.calculateIncomeOfProject(projectDto.getId())));
        response.setProjects(collect);
        response.setPage(page);
        response.setPageCount((int) Math.ceil((double) projectRepository.count() / (double) countOnPage));

        return response;
    }

    @Override
    @Transactional
    public void deleteProject(Project project) {
        projectRepository.delete(project);
    }

    @Override
    public ByteArrayResource generateReport() {
        List<Project> projects = projectRepository.findAll();
        try (Workbook wb = new SXSSFWorkbook(); ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream()) {
            int rowCount = 0;
            final String[] headers = {"№", "name", "cost", "department_id", "date_beg", "date_end", "date_end_real"};
            SXSSFSheet sheet = (SXSSFSheet) wb.createSheet("projects");
            Row row = sheet.createRow(rowCount++);
            for (int i = 0; i < headers.length; i++) {
                row.createCell(i).setCellValue(headers[i]);
            }

            for (Project project : projects) {
                Row projectRow = sheet.createRow(rowCount++);
                projectRow.createCell(0).setCellValue(project.getId());
                projectRow.createCell(1).setCellValue(project.getName());
                projectRow.createCell(2).setCellValue(project.getCost());
                projectRow.createCell(3).setCellValue(project.getDepartment().getId());
                projectRow.createCell(4).setCellValue(DateFormatUtils.formatTimeStamp(project.getDateBeg(), "dd-MM-yyyy"));
                projectRow.createCell(5).setCellValue(DateFormatUtils.formatTimeStamp(project.getDateEnd(), "dd-MM-yyyy"));
                if (project.getDateEndReal() != null) {
                    projectRow.createCell(6).setCellValue(DateFormatUtils.formatTimeStamp(project.getDateEndReal(), "dd-MM-yyyy"));
                }

            }

            wb.write(arrayOutputStream);
            return new ByteArrayResource(arrayOutputStream.toByteArray());
        } catch (IOException e) {
            return null;
        }


    }

    @Override
    @Transactional
    public Long createProject(Project project) {
        return projectRepository.save(project).getId();
    }

    @Override
    public Optional<Project> getProject(long id) {
        return projectRepository.findById(id);
    }
}
