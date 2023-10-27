package ru.cource.accounting.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.cource.accounting.dto.models.EmployeeDto;
import ru.cource.accounting.dto.models.GetEmployeesResponse;
import ru.cource.accounting.models.Employee;
import ru.cource.accounting.repository.EmployeeRepository;
import ru.cource.accounting.service.EmployeesService;

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
    public Long createEmployee(Employee employee) {
        return employeeRepository.save(employee).getId();
    }

    @Override
    public Optional<Employee> getEmployee(long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }
}
