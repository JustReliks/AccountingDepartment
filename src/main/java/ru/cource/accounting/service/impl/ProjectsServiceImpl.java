package ru.cource.accounting.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.cource.accounting.dto.models.GetProjectsResponse;
import ru.cource.accounting.dto.models.ProjectDto;
import ru.cource.accounting.models.Project;
import ru.cource.accounting.repository.ProjectRepository;
import ru.cource.accounting.service.ProjectsService;

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
    public void deleteProject(Project project) {
        projectRepository.delete(project);
    }

    @Override
    public Long createProject(Project project) {
        return projectRepository.save(project).getId();
    }

    @Override
    public Optional<Project> getProject(long id) {
        return projectRepository.findById(id);
    }
}
