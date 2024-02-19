package ru.cource.accounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.cource.accounting.models.Department;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByName(String name);

    @Query(value = "SELECT * FROM departments p ORDER BY id", nativeQuery = true)
    Page<Department> loadListOfDepartments(Pageable pageable);

    @Query(value = "SELECT id FROM find_employees_for_department(?1)", nativeQuery = true)
    List<Long> getEmployees(long id);


}
