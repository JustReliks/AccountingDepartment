package ru.cource.accounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.cource.accounting.dto.models.DepartmentDto;
import ru.cource.accounting.dto.models.EmployeeDto;
import ru.cource.accounting.dto.models.GetEmployeesResponse;
import ru.cource.accounting.models.Department;
import ru.cource.accounting.models.Employee;
import ru.cource.accounting.service.DepartmentService;
import ru.cource.accounting.service.EmployeesService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@Controller
@RequestMapping("/api/employees")
@RequiredArgsConstructor

public class EmployeesController {

    private final EmployeesService employeesService;
    private final DepartmentService departmentService;

    @GetMapping("/load")
    public ResponseEntity<GetEmployeesResponse> loadEmployees(@RequestParam(name = "page") int page, @RequestParam(name = "count") int count) {
        if (page < 0 || count < 0) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(employeesService.getEmployees(page, count));
    }

    @PostMapping("/save")
    public ResponseEntity<Long> createEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee employee = new Employee();
        Set<Department> departments = new HashSet<>();
        for (DepartmentDto dto : employeeDto.getDepartments()) {
            Optional<Department> departmentOpt = departmentService.getDepartment(dto.getId());
            if (departmentOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            departments.add(departmentOpt.get());
        }
        employee.setDepartments(departments);
        employee.setPosition(employeeDto.getPosition());
        employee.setFirstName(employee.getFirstName());
        employee.setLastname(employee.getLastname());
        employee.setFatherName(employee.getFatherName());
        employee.setSalary(employeeDto.getSalary());

        return ResponseEntity.ok(employeesService.createEmployee(employee));
    }

    @PostMapping("/update")
    public ResponseEntity<Long> updateEmployee(@RequestBody EmployeeDto employeeDto) {
        Optional<Employee> employeeOpt = employeesService.getEmployee(employeeDto.getId());
        if (employeeOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Employee employee = employeeOpt.get();
        Set<Department> departments = new HashSet<>();
        for (DepartmentDto dto : employeeDto.getDepartments()) {
            Optional<Department> departmentOpt = departmentService.getDepartment(dto.getId());
            if (departmentOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            departments.add(departmentOpt.get());
        }
        employee.setDepartments(departments);
        employee.setPosition(employeeDto.getPosition());
        employee.setFirstName(employee.getFirstName());
        employee.setLastname(employee.getLastname());
        employee.setFatherName(employee.getFatherName());
        employee.setSalary(employeeDto.getSalary());

        return ResponseEntity.ok(employeesService.createEmployee(employee));
    }

    @PostMapping("/delete")
    public ResponseEntity<Boolean> deleteEmployee(@RequestParam(name = "id") long id) {
        Optional<Employee> employeeOptional = employeesService.getEmployee(id);
        if (employeeOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
        try {
            employeesService.deleteEmployee(employeeOptional.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(false);
        }
        return ResponseEntity.ok(true);
    }
}
