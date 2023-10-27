package ru.cource.accounting.service;

import ru.cource.accounting.dto.models.GetDepartmentsResponse;
import ru.cource.accounting.models.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    Optional<Department> findByName(String name);

    List<Department> getAllDepartments();

    GetDepartmentsResponse getDepartments(int page, int count);

    Long createDepartment(Department department);

    Optional<Department> getDepartment(long id);

    void deleteDepartment(Department department);

}
