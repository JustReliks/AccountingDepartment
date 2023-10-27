package ru.cource.accounting.service;

import ru.cource.accounting.dto.models.GetProjectsResponse;
import ru.cource.accounting.models.Project;

import java.util.Optional;

public interface ProjectsService {

    GetProjectsResponse getProjects(int page, int countOnPage);
    Long createProject(Project project);

    Optional<Project> getProject(long id);

    void deleteProject(Project project);

}
