package pl.jakubdudek.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.jakubdudek.springboot.entity.Teacher;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT t FROM Teacher t WHERE t.group.id = :groupId")
    List<Teacher> getTeachersByGroupId(@Param("groupId") Long groupId);

    @Query("SELECT COUNT(t) FROM Teacher t WHERE t.group.id = :groupId")
    Long getCountTeachersByGroupId(@Param("groupId") Long groupId);
}