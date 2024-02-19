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
import ru.cource.accounting.dto.models.DepartmentDto;
import ru.cource.accounting.dto.models.GetDepartmentsResponse;
import ru.cource.accounting.models.Department;
import ru.cource.accounting.models.Employee;
import ru.cource.accounting.repository.DepartmentRepository;
import ru.cource.accounting.service.DepartmentService;
import ru.cource.accounting.service.EmployeesService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeesService employeesService;

    @Override
    public Optional<Department> findByName(String name) {
        return departmentRepository.findByName(name);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public GetDepartmentsResponse getDepartments(int page, int count) {
        List<Department> projects = departmentRepository.loadListOfDepartments(PageRequest.of(page, count, Sort.DEFAULT_DIRECTION, "id")).stream().toList();
        GetDepartmentsResponse response = new GetDepartmentsResponse();
        Stream<DepartmentDto> projectDtoStream = projects.stream().map(DepartmentDto::toDto);
        List<DepartmentDto> collect = projectDtoStream.collect(Collectors.toList());
        response.setDepartments(collect);
        response.setPage(page);
        response.setPageCount((int) Math.ceil((double) departmentRepository.count() / (double) count));

        return response;

    }

    @Override
    @Transactional
    public Long createDepartment(Department department) {
        return departmentRepository.save(department).getId();
    }

    @Override
    public Optional<Department> getDepartment(long id) {
        return departmentRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteDepartment(Department department) {
        departmentRepository.delete(department);
    }

    @Override
    public List<Employee> findEmployeesForDepartment(long id) {
        return departmentRepository.getEmployees(id).stream().map(id1 -> employeesService.getEmployee(id1).orElseThrow()).collect(Collectors.toList());
    }

    @Override
    public ByteArrayResource generateReport() {

        List<Department> projects = departmentRepository.findAll();
        try (Workbook wb = new SXSSFWorkbook(); ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream()) {
            int rowCount = 0;
            final String[] headers = {"№", "name"};
            SXSSFSheet sheet = (SXSSFSheet) wb.createSheet("departments");
            Row row = sheet.createRow(rowCount++);
            for (int i = 0; i < headers.length; i++) {
                row.createCell(i).setCellValue(headers[i]);
            }

            for (Department department : projects) {
                Row projectRow = sheet.createRow(rowCount++);
                projectRow.createCell(0).setCellValue(department.getId());
                projectRow.createCell(1).setCellValue(department.getName());

            }

            wb.write(arrayOutputStream);
            return new ByteArrayResource(arrayOutputStream.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }
}
