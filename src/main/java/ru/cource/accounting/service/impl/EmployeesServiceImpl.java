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
import ru.cource.accounting.dto.models.EmployeeDto;
import ru.cource.accounting.dto.models.GetEmployeesResponse;
import ru.cource.accounting.models.Employee;
import ru.cource.accounting.repository.EmployeeRepository;
import ru.cource.accounting.service.EmployeesService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeesServiceImpl implements EmployeesService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public GetEmployeesResponse getEmployees(int page, int count) {
        List<Employee> employees = employeeRepository.loadListOfEmployees(PageRequest.of(page, count, Sort.DEFAULT_DIRECTION, "id")).stream().toList();
        GetEmployeesResponse response = new GetEmployeesResponse();
        response.setEmployees(employees.stream().map(EmployeeDto::toDto).collect(Collectors.toList()));
        response.setPage(page);
        response.setPageCount((int) Math.ceil((double) employeeRepository.count() / (double) count));

        return response;
    }

    @Override
    @Transactional
    public Long createEmployee(Employee employee) {
        return employeeRepository.save(employee).getId();
    }

    @Override
    public Optional<Employee> getEmployee(long id) {
        return employeeRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }

    @Override
    public ByteArrayResource generateReport() {
        List<Employee> projects = employeeRepository.findAll();
        try (Workbook wb = new SXSSFWorkbook(); ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream()) {
            int rowCount = 0;
            final String[] headers = {"№", "first_name", "last_name", "father_name", "position", "salary"};
            SXSSFSheet sheet = (SXSSFSheet) wb.createSheet("employees");
            Row row = sheet.createRow(rowCount++);
            for (int i = 0; i < headers.length; i++) {
                row.createCell(i).setCellValue(headers[i]);
            }

            for (Employee employee : projects) {
                Row projectRow = sheet.createRow(rowCount++);
                projectRow.createCell(0).setCellValue(employee.getId());
                projectRow.createCell(1).setCellValue(employee.getFirstName());
                projectRow.createCell(2).setCellValue(employee.getLastname());
                projectRow.createCell(3).setCellValue(employee.getFatherName());
                projectRow.createCell(4).setCellValue(employee.getPosition());
                projectRow.createCell(5).setCellValue(employee.getSalary());

            }

            wb.write(arrayOutputStream);
            return new ByteArrayResource(arrayOutputStream.toByteArray());
        } catch (IOException e) {
            return null;
        }

    }

}
