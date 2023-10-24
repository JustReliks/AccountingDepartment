package ru.cource.accounting.service;

import ru.cource.accounting.models.Department;

import java.util.Optional;

public interface DepartmentService {

    Optional<Department> findByName(String name);

}
