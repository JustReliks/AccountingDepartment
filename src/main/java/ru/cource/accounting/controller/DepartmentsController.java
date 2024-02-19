package ru.cource.accounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.cource.accounting.dto.models.DepartmentDto;
import ru.cource.accounting.dto.models.EmployeeDto;
import ru.cource.accounting.dto.models.GetAllDepartmentsResponse;
import ru.cource.accounting.dto.models.GetDepartmentsResponse;
import ru.cource.accounting.models.Department;
import ru.cource.accounting.models.Employee;
import ru.cource.accounting.service.DepartmentService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@Controller
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentsController {

    private final DepartmentService departmentService;

    @GetMapping("/load-departments")
    public ResponseEntity<GetAllDepartmentsResponse> loadDepartments() {

        return ResponseEntity.ok(GetAllDepartmentsResponse.toDto(departmentService.getAllDepartments()));
    }

    @GetMapping("/load")
    public ResponseEntity<GetDepartmentsResponse> loadDepartments(@RequestParam(name = "page") int page, @RequestParam(name = "count") int count) {
        if (page < 0 || count < 0) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(departmentService.getDepartments(page, count));
    }

    @PostMapping("/save")
    public ResponseEntity<Long> createDepartment(@RequestBody DepartmentDto departmentDto) {
        Department department = new Department();

        department.setName(departmentDto.getName());
        try {
            return ResponseEntity.ok(departmentService.createDepartment(department));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/employees")
    public ResponseEntity<EmployeeDto[]> getEmployeesForDepartment(@RequestParam(name = "id") long id) {
        List<Employee> employeesForDepartment = departmentService.findEmployeesForDepartment(id);
        try {
            return ResponseEntity.ok
                    (employeesForDepartment
                            .stream().map(EmployeeDto::toDto).toList().toArray(new EmployeeDto[employeesForDepartment.size()]));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Long> updateDepartment(@RequestBody DepartmentDto departmentDto) {
        Optional<Department> departmentOpt = departmentService.getDepartment(departmentDto.getId());
        if (departmentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Department department = departmentOpt.get();

        department.setName(departmentDto.getName());
        try {
            return ResponseEntity.ok(departmentService.createDepartment(department));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/report")
    public ResponseEntity<ByteArrayResource> generateReport() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "force-download"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Report.xlsx");
        return new ResponseEntity<>(departmentService.generateReport(), headers, HttpStatus.CREATED);
    }
    @PostMapping("/delete")
    public ResponseEntity<Boolean> deleteDepartment(@RequestParam(name = "id") long id) {
        Optional<Department> department = departmentService.getDepartment(id);
        if (department.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
        try {
            departmentService.deleteDepartment(department.get());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
        return ResponseEntity.ok(true);
    }
}
