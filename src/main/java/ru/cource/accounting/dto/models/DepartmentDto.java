package ru.cource.accounting.dto.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.cource.accounting.models.Department;

@Data
@Getter
@Setter
public class DepartmentDto {

    private long id;
    private String name;

    public static DepartmentDto toDto(Department department) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(department.getId());
        dto.setName(department.getName());

        return dto;
    }

}
