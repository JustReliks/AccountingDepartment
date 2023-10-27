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

    public static final String format = "yyyy-MM-dd";

    private long id;
    private String name;
    private double cost;
    private String departmentName;
    private String dateBeg;
    private String dateEnd;
    private String dateEndReal;
    private double income;

    public static ProjectDto toDto(Project project) {
        ProjectDto dto = new ProjectDto();
        dto.setCost(project.getCost());
        dto.setName(project.getName());
        dto.setId(project.getId());
        dto.setDepartmentName(project.getDepartment().getName());
        dto.setDateBeg(DateFormatUtils.formatTimeStamp(project.getDateBeg(), format));
        if (project.getDateEnd() != null) {
            dto.setDateEnd(DateFormatUtils.formatTimeStamp(project.getDateEnd(), format));
        }
        if(project.getDateEndReal() != null) {
            dto.setDateEndReal(DateFormatUtils.formatTimeStamp(project.getDateEndReal(), format));
        }

        return dto;

    }

}
