package ru.cource.accounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.cource.accounting.dto.models.GetProjectsResponse;
import ru.cource.accounting.dto.models.ProjectDto;
import ru.cource.accounting.models.Department;
import ru.cource.accounting.models.Project;
import ru.cource.accounting.service.DepartmentService;
import ru.cource.accounting.service.ProjectsService;
import ru.cource.accounting.utils.DateFormatUtils;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectsController {

    private final ProjectsService projectsService;
    private final DepartmentService departmentService;

    @GetMapping("/load")
    public ResponseEntity<GetProjectsResponse> loadProjects(@RequestParam int page, @RequestParam int count) {
        return ResponseEntity.ok(projectsService.getProjects(page, count));
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createProject(@RequestBody ProjectDto projectDto) {
        Project project = new Project();
        Optional<Department> departmentOpt = departmentService.findByName(projectDto.getDepartmentName());
        if (departmentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        project.setDepartment(departmentOpt.get());
        project.setCost(projectDto.getCost());
        project.setName(projectDto.getName());
        project.setDateBeg(DateFormatUtils.formatToTimeStamp(projectDto.getDateBeg(), "dd/MM/yyyy"));
        project.setDateEnd(DateFormatUtils.formatToTimeStamp(projectDto.getDateEnd(), "dd/MM/yyyy"));
        projectsService.createProject(project);

        return ResponseEntity.ok(projectsService.createProject(project));
    }


}
