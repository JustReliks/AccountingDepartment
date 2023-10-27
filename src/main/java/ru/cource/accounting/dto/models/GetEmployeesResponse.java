package ru.cource.accounting.dto.models;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class GetEmployeesResponse {

    private int pageCount;
    private int page;
    private List<EmployeeDto> employees;


}
