package ru.cource.accounting.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.cource.accounting.dto.models.DepartmentDto;
import ru.cource.accounting.dto.models.GetDepartmentsResponse;
import ru.cource.accounting.models.Department;
import ru.cource.accounting.repository.DepartmentRepository;
import ru.cource.accounting.service.DepartmentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public Optional<Department> findByName(String name) {
        return departmentRepository.findByName(name);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public GetDepartmentsResponse getDepartments(int page, int count) {
        List<Department> projects = departmentRepository.loadListOfDepartments(PageRequest.of(page, count, Sort.DEFAULT_DIRECTION, "id")).stream().toList();
        GetDepartmentsResponse response = new GetDepartmentsResponse();
        Stream<DepartmentDto> projectDtoStream = projects.stream().map(DepartmentDto::toDto);
        List<DepartmentDto> collect = projectDtoStream.collect(Collectors.toList());
        response.setDepartments(collect);
        response.setPage(page);
        response.setPageCount((int) Math.ceil((double) departmentRepository.count() / (double) count));

        return response;

    }

    @Override
    public Long createDepartment(Department department) {
        return departmentRepository.save(department).getId();
    }

    @Override
    public Optional<Department> getDepartment(long id) {
        return departmentRepository.findById(id);
    }

    @Override
    public void deleteDepartment(Department department) {
        departmentRepository.delete(department);
    }
}
