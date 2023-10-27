package ru.cource.accounting.dto.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.cource.accounting.models.Department;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
public class GetAllDepartmentsResponse {

    private List<DepartmentDto> departments;

    public static GetAllDepartmentsResponse toDto(List<Department> departments) {
        GetAllDepartmentsResponse response = new GetAllDepartmentsResponse();
        response.setDepartments(departments.stream().map(DepartmentDto::toDto).collect(Collectors.toList()));

        return response;
    }

}
