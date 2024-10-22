package com.example.restapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllStudents() {
        Student student1 = new Student("John Doe", "john@example.com");
        Student student2 = new Student("Jane Doe", "jane@example.com");

        when(studentService.getAllStudents()).thenReturn(Arrays.asList(student1, student2));

        List<Student> students = studentController.getAllStudents();

        assertEquals(2, students.size());
    }

    @Test
    void testGetStudentById() {
        Student student = new Student("John Doe", "john@example.com");
        when(studentService.getStudentById(1L)).thenReturn(student);

        ResponseEntity<Student> response = studentController.getStudentById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John Doe", response.getBody().getName());
    }

    @Test
    void testCreateStudent() {
        Student student = new Student("John Doe", "john@example.com");

        when(studentService.createStudent(any(Student.class))).thenReturn(student);

        Student created = studentController.createStudent(student);

        assertEquals("null", created.getName());
    }

    // Add tests for update and delete similarly...
}

