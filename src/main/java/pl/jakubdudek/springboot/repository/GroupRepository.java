package pl.jakubdudek.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import pl.jakubdudek.springboot.entity.Group;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("SELECT (COUNT(t)*100)/g.maxTeachers FROM Group g LEFT JOIN g.teachers t WHERE g.id = :id")
    Optional<Double> getGroupPercentage(@Param("id") Long id);
}
