package ru.cource.accounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
        for (long departId : employeeDto.getDepartments()) {
            Optional<Department> departmentOpt = departmentService.getDepartment(departId);
            if (departmentOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            departments.add(departmentOpt.get());
        }
        employee.setDepartments(departments);
        employee.setPosition(employeeDto.getPosition());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastname(employeeDto.getLastName());
        employee.setFatherName(employeeDto.getFatherName());
        employee.setSalary(employeeDto.getSalary());
        try {
            return ResponseEntity.ok(employeesService.createEmployee(employee));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Long> updateEmployee(@RequestBody EmployeeDto employeeDto) {
        Optional<Employee> employeeOpt = employeesService.getEmployee(employeeDto.getId());
        if (employeeOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Employee employee = employeeOpt.get();
        Set<Department> departments = new HashSet<>();
        for (long departId : employeeDto.getDepartments()) {
            Optional<Department> departmentOpt = departmentService.getDepartment(departId);
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
        try {
            return ResponseEntity.ok(employeesService.createEmployee(employee));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<Boolean> deleteEmployee(@RequestParam(name = "id") long id) {
        Optional<Employee> employeeOptional = employeesService.getEmployee(id);
        if (employeeOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
        try {
            employeesService.deleteEmployee(employeeOptional.get());
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
    @GetMapping("/report")
    public ResponseEntity<ByteArrayResource> generateReport() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "force-download"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Report.xlsx");
        return new ResponseEntity<>(employeesService.generateReport(), headers, HttpStatus.CREATED);
    }

}
