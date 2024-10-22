package com.example.restapi;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStudent() {
        Student student = new Student("John Doe", "john@example.com");

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student created = studentService.createStudent(student);

        assertEquals("John Doe", created.getName());
    }

    @Test
    void testGetAllStudents() {
        Student student1 = new Student("John Doe", "john@example.com");
        Student student2 = new Student("Jane Doe", "jane@example.com");

        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));

        List<Student> students = studentService.getAllStudents();

        assertEquals(2, students.size());
    }

    @Test
    void testGetStudentById() {
        Student student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setEmail("john@example.com");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student found = studentService.getStudentById(1L);

        assertEquals("John Doe", found.getName(), "Expected student name to be 'John Doe'");
        assertEquals("john@example.com", found.getEmail(), "Expected student email to be 'john@example.com'");
    }


    @Test
    void testUpdateStudent() {
        Student existingStudent = new Student("John Doe", "john@example.com");
        Student updatedDetails = new Student("John Smith", "johnsmith@example.com");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedDetails);

        Student updatedStudent = studentService.updateStudent(1L, updatedDetails);

        assertEquals("John Smith", updatedStudent.getName());
    }

    @Test
    void testDeleteStudent() {
        doNothing().when(studentRepository).deleteById(1L);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }
}
