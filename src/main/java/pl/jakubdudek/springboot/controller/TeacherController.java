package pl.jakubdudek.springboot.controller;

import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakubdudek.springboot.repository.GroupRepository;
import pl.jakubdudek.springboot.repository.TeacherRepository;
import pl.jakubdudek.springboot.entity.Group;
import pl.jakubdudek.springboot.entity.Teacher;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    GroupRepository groupRepository;

    @PostMapping("/")
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        Group group = groupRepository.findById(teacher.getGroupId()).orElse(null);

        if(group == null) {
            return new ResponseEntity("Group not found", HttpStatus.NOT_FOUND);
        }
        else {
            teacher.setGroup(group);

            Teacher savedTeacher = teacherRepository.save(teacher);
            return ResponseEntity.ok(savedTeacher);
        }
    }

    @GetMapping("/csv")
    public void exportTeachersCsv(HttpServletResponse response) throws IOException {
        List<Teacher> teacherEntities = teacherRepository.findAll();

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=teachers.csv");

        PrintWriter writer = response.getWriter();
        CSVWriter csvWriter = new CSVWriter(writer);
        csvWriter.writeNext(new String[]{"ID", "Name", "LastName", "Condition", "BirthYear", "Salary", "GroupID"});

        for(Teacher teacher : teacherEntities) {
            csvWriter.writeNext(new String[]{
                    Long.toString(teacher.getId()),
                    teacher.getName(),
                    teacher.getLastName(),
                    teacher.getCondition().toString(),
                    Integer.toString(teacher.getBirthYear()),
                    Integer.toString(teacher.getSalary()),
                    Long.toString(teacher.getGroup().getId())
            });
        }
        csvWriter.close();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long id) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);

        if(teacher == null) {
            return new ResponseEntity("Teacher not found", HttpStatus.NOT_FOUND);
        }
        else {
            teacherRepository.delete(teacher);
            return new ResponseEntity("Teacher deleted successfully", HttpStatus.OK);
        }
    }
}