package ru.cource.accounting.dto.models;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.cource.accounting.models.Department;
import ru.cource.accounting.models.Employee;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
public class EmployeeDto {

    private long id;
    private String firstName;
    private String lastName;
    private String fatherName;
    private String position;
    private double salary;
    private List<Long> departments;

    public static EmployeeDto toDto(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setSalary(employee.getSalary());
        dto.setPosition(employee.getPosition());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastname());
        dto.setFatherName(employee.getFatherName());

        dto.setDepartments(employee.getDepartments().stream().map(Department::getId).collect(Collectors.toList()));

        return dto;
    }

}
