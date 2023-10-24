package ru.cource.accounting.service;

import ru.cource.accounting.dto.models.GetProjectsResponse;
import ru.cource.accounting.models.Project;

public interface ProjectsService {

    GetProjectsResponse getProjects(int page, int countOnPage);
    Long createProject(Project project);

}
