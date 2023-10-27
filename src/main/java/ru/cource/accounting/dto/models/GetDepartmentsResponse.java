package ru.cource.accounting.dto.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class GetDepartmentsResponse {

    private int pageCount;
    private int page;
    private List<DepartmentDto> departments;

}
