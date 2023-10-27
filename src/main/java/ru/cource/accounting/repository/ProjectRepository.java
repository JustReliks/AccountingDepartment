package ru.cource.accounting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.cource.accounting.models.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query(value = "SELECT * FROM projects p ORDER BY id", nativeQuery = true)
    Page<Project> loadListOfProjects(Pageable pageable);

    @Query(value = "SELECT * FROM calculate_profit(?1)", nativeQuery = true)
    double calculateIncomeOfProject(long id);

}
