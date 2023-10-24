package ru.cource.accounting.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cource.accounting.models.Department;
import ru.cource.accounting.repository.DepartmentRepository;
import ru.cource.accounting.service.DepartmentService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    @Override
    public Optional<Department> findByName(String name) {
        return departmentRepository.findByName(name);
    }
}
