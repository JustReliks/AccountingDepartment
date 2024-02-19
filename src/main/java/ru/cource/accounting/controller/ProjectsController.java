package ru.cource.accounting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.cource.accounting.dto.models.GetProjectsResponse;
import ru.cource.accounting.dto.models.ProjectDto;
import ru.cource.accounting.models.Department;
import ru.cource.accounting.models.Project;
import ru.cource.accounting.service.DepartmentService;
import ru.cource.accounting.service.ProjectsService;
import ru.cource.accounting.utils.DateFormatUtils;

import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@Controller
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectsController {

    private final ProjectsService projectsService;
    private final DepartmentService departmentService;

    @GetMapping("/load")
    public ResponseEntity<GetProjectsResponse> loadProjects(@RequestParam(name = "page") int page, @RequestParam(name = "count") int count) {
        if (page < 0 || count < 0) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(projectsService.getProjects(page, count));
    }


    @PostMapping("/save")
    public ResponseEntity<Long> createProject(@RequestBody ProjectDto projectDto) {
        Project project = new Project();
        Optional<Department> departmentOpt = departmentService.findByName(projectDto.getDepartmentName());
        if (departmentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        project.setDepartment(departmentOpt.get());
        project.setCost(projectDto.getCost());
        project.setName(projectDto.getName());
        project.setDateBeg(DateFormatUtils.formatToTimeStamp(projectDto.getDateBeg(), ProjectDto.format));
        project.setDateEnd(DateFormatUtils.formatToTimeStamp(projectDto.getDateEnd(), ProjectDto.format));
        if (StringUtils.hasText(projectDto.getDateEndReal())) {
            project.setDateEndReal(DateFormatUtils.formatToTimeStamp(projectDto.getDateEndReal(), ProjectDto.format));
        }

        try {
            return ResponseEntity.ok(projectsService.createProject(project));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/change")
    public ResponseEntity<Long> changeProject(@RequestBody ProjectDto projectDto) {
        Optional<Project> projectOpt = projectsService.getProject(projectDto.getId());
        if (projectOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Project project = projectOpt.get();
        Optional<Department> departmentOpt = departmentService.findByName(projectDto.getDepartmentName());
        if (departmentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        project.setDepartment(departmentOpt.get());
        project.setCost(projectDto.getCost());
        project.setName(projectDto.getName());
        project.setDateBeg(DateFormatUtils.formatToTimeStamp(projectDto.getDateBeg(), ProjectDto.format));
        project.setDateEnd(DateFormatUtils.formatToTimeStamp(projectDto.getDateEnd(), ProjectDto.format));
        if (StringUtils.hasText(projectDto.getDateEndReal())) {
            project.setDateEndReal(DateFormatUtils.formatToTimeStamp(projectDto.getDateEndReal(), ProjectDto.format));
        }
        try {
            return ResponseEntity.ok(projectsService.createProject(project));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/report")
    public ResponseEntity<ByteArrayResource> generateReport() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "force-download"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Report.xlsx");
        return new ResponseEntity<>(projectsService.generateReport(), headers, HttpStatus.CREATED);
    }

    @PostMapping("/delete")
    public ResponseEntity<Boolean> deleteProject(@RequestParam(name = "id") long id) {
        Optional<Project> project = projectsService.getProject(id);
        if (project.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
        try {
            projectsService.deleteProject(project.get());
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
