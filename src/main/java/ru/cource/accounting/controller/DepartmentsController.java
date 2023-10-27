package ru.cource.accounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.cource.accounting.dto.models.DepartmentDto;
import ru.cource.accounting.dto.models.GetAllDepartmentsResponse;
import ru.cource.accounting.dto.models.GetDepartmentsResponse;
import ru.cource.accounting.models.Department;
import ru.cource.accounting.service.DepartmentService;

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

        return ResponseEntity.ok(departmentService.createDepartment(department));
    }

    @PostMapping("/update")
    public ResponseEntity<Long> updateDepartment(@RequestBody DepartmentDto departmentDto) {
        Optional<Department> departmentOpt = departmentService.getDepartment(departmentDto.getId());
        if (departmentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Department department = departmentOpt.get();

        department.setName(departmentDto.getName());

        return ResponseEntity.ok(departmentService.createDepartment(department));
    }

    @PostMapping("/delete")
    public ResponseEntity<Boolean> deleteDepartment(@RequestParam(name = "id") long id) {
        Optional<Department> department = departmentService.getDepartment(id);
        if (department.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
        try {
            departmentService.deleteDepartment(department.get());

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(false);
        }
        return ResponseEntity.ok(true);
    }


}
