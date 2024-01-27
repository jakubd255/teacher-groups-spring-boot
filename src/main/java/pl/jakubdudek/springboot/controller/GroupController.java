package pl.jakubdudek.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakubdudek.springboot.repository.GroupRepository;
import pl.jakubdudek.springboot.entity.Group;
import pl.jakubdudek.springboot.entity.Teacher;
import pl.jakubdudek.springboot.repository.TeacherRepository;

import java.util.List;

@RestController
@RequestMapping("/api/group")
public class GroupController {
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @PostMapping("/")
    ResponseEntity<Group> addGroup(@RequestBody Group group) {
        Group savedGroup = groupRepository.save(group);

        return ResponseEntity.ok(savedGroup);
    }

    @GetMapping("/")
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupRepository.findAll();

        if(groups.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity(groups, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}/teacher")
    public ResponseEntity<List<Teacher>> getTeachersByGroupId(@PathVariable Long id) {
        Group group = groupRepository.findById(id).orElse(null);

        if(group == null) {
            return new ResponseEntity("Group not found", HttpStatus.NOT_FOUND);
        }
        else {
            List<Teacher> teachers = teacherRepository.getTeachersByGroupId(id);
            return new ResponseEntity(teachers, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}/fill")
    public ResponseEntity<Double> getPercentage(@PathVariable Long id) {
        Double percentage = groupRepository.getGroupPercentage(id).orElse(null);

        if(percentage == null) {
            return new ResponseEntity("Group not found", HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity(percentage, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable Long id) {
        Group group = groupRepository.findById(id).orElse(null);

        if(group == null) {
            return new ResponseEntity("Group not found", HttpStatus.NOT_FOUND);
        }
        else {
            groupRepository.delete(group);
            return new ResponseEntity("Group deleted successfully", HttpStatus.OK);
        }
    }
}