package ru.cource.accounting.dto.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.cource.accounting.models.Project;
import ru.cource.accounting.utils.DateFormatUtils;

@Data
@Getter
@Setter
public class ProjectDto {

    private String name;
    private double cost;
    private String departmentName;
    private String dateBeg;
    private String dateEnd;
    private String dateEndReal;

    public static ProjectDto toDto(Project project) {
        ProjectDto dto = new ProjectDto();
        dto.setCost(project.getCost());
        dto.setName(project.getName());
        dto.setDepartmentName(project.getDepartment().getName());
        dto.setDateBeg(DateFormatUtils.formatTimeStamp(project.getDateBeg(), "dd/MM/yyyy"));
        if (project.getDateEnd() != null) {
            dto.setDateEnd(DateFormatUtils.formatTimeStamp(project.getDateEnd(), "dd/MM/yyyy"));
        }
        if(project.getDateEndReal() != null) {
            dto.setDateEndReal(DateFormatUtils.formatTimeStamp(project.getDateEndReal(), "dd/MM/yyyy"));
        }

        return dto;

    }

}
