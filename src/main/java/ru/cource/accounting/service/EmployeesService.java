package ru.cource.accounting.service;

import org.springframework.core.io.ByteArrayResource;
import ru.cource.accounting.dto.models.GetEmployeesResponse;
import ru.cource.accounting.models.Department;
import ru.cource.accounting.models.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeesService {


    List<Employee> getAllEmployees();

    GetEmployeesResponse getEmployees(int page, int count);

    Long createEmployee(Employee employee);

    Optional<Employee> getEmployee(long id);

    void deleteEmployee(Employee employee);

    ByteArrayResource generateReport();


}
