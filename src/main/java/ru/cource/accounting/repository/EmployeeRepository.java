package ru.cource.accounting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.cource.accounting.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
