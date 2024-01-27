package pl.jakubdudek.springboot.controller;

import com.google.gson.Gson;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.jakubdudek.springboot.entity.Group;
import pl.jakubdudek.springboot.entity.Teacher;
import pl.jakubdudek.springboot.enumerate.TeacherCondition;
import pl.jakubdudek.springboot.repository.GroupRepository;
import pl.jakubdudek.springboot.repository.TeacherRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeacherControllerTest {
    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private TeacherController teacherController;

    private MockMvc mockMvc;

    public TeacherControllerTest() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(teacherController).build();
    }

    @Test
    public void testCreateTeacherIsOk() throws Exception {
        Group group = new Group();
        when(groupRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(group));
        when(teacherRepository.save(any(Teacher.class))).thenReturn(new Teacher());

        Teacher teacher = new Teacher(1L, "John", "Smith", TeacherCondition.PRESENT, 1990, 3000, 1L);

        Gson gson = new Gson();
        String json = gson.toJson(teacher);

        mockMvc.perform(post("/api/teacher/")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()
        );

        verify(groupRepository).findById(1L);
        verify(teacherRepository).save(any(Teacher.class));
    }

    @Test
    public void testDeleteTeacherIsOk() {
        Long teacherId = 1L;
        Teacher teacher = new Teacher();

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.ofNullable(teacher));

        ResponseEntity<String> response = teacherController.deleteTeacher(teacherId);

        verify(teacherRepository).findById(teacherId);
        verify(teacherRepository).delete(teacher);

        assertEquals("Teacher deleted successfully", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteTeacherNotFound() {
        Long teacherId = 1L;

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        ResponseEntity<String> response = teacherController.deleteTeacher(teacherId);

        verify(teacherRepository).findById(teacherId);
        verify(teacherRepository, never()).delete(any());

        assertEquals("Teacher not found", response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
