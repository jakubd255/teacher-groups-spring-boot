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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GroupControllerTest {
    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupController groupController;

    private MockMvc mockMvc;

    public GroupControllerTest() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();
    }

    @Test
    public void testCreateGroupIsOK() throws Exception {
        Group group = new Group("grupa1", 10);

        when(groupRepository.save(any(Group.class))).thenReturn(new Group());

        Gson gson = new Gson();
        String json = gson.toJson(group);

        mockMvc.perform(
                post("/api/group/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
        .andExpect(status().isOk());

        verify(groupRepository).save(any(Group.class));
    }

    @Test
    public void testGetAllGroups() {
        List<Group> groups = Arrays.asList(
                new Group(1L, "grupa1", 10),
                new Group(1L, "grupa2", 12),
                new Group(1L, "grupa3", 8)
        );
        when(groupRepository.findAll()).thenReturn(groups);

        ResponseEntity<List<Group>> response = groupController.getAllGroups();

        assertEquals(groups, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetTeachersByGroupId() {
        Group group = new Group(1L, "grupa1", 10);

        List<Teacher> teachers = Arrays.asList(
                new Teacher(1L, "John", "Smith", TeacherCondition.PRESENT, 1990, 3000, 1L),
                new Teacher(2L, "John", "Smith", TeacherCondition.PRESENT, 1987, 3500, 1L)
        );

        when(groupRepository.findById(group.getId())).thenReturn(java.util.Optional.ofNullable(group));
        when(teacherRepository.findAll()).thenReturn(teachers);

        ResponseEntity<List<Teacher>> response = groupController.getTeachersByGroupId(group.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteGroupIsOk() {
        Group group = new Group(1L, "grupa1", 10);

        when(groupRepository.findById(group.getId())).thenReturn(java.util.Optional.ofNullable(group));

        ResponseEntity<String> response = groupController.deleteGroup(group.getId());

        verify(groupRepository).findById(group.getId());
        verify(groupRepository).delete(group);

        assertEquals("Group deleted successfully", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteGroupNotFound() {
        Group group = new Group(1L, "grupa1", 10);
        when(groupRepository.findById(group.getId())).thenReturn(java.util.Optional.empty());

        ResponseEntity<String> response = groupController.deleteGroup(group.getId());

        verify(groupRepository).findById(group.getId());
        verify(groupRepository, never()).delete(any());

        assertEquals("Group not found", response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
