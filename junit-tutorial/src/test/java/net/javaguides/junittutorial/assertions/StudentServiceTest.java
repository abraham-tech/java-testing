package net.javaguides.junittutorial.assertions;

import net.javaguides.junittutorial.Student;
import net.javaguides.junittutorial.StudentService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    @Test
    void getStudentsTest() {
        StudentService studentService = new StudentService();
        List<Student> listOfStudents = studentService.getStudents();
        Student student = new Student(4, "Ab");
        listOfStudents.add(student);
        boolean actualResult = listOfStudents.isEmpty();
//        assertTrue(actualResult);
//        assertTrue(() ->
//            actualResult
//        );
//        assertTrue(actualResult, "The list of students is empty");
//        assertTrue(()->actualResult, "List is empty");
//        assertTrue(actualResult, () -> "List of students is empty");

    }

    @Test
    void getStudentsTestUsingAssertFalse() {
        StudentService studentService = new StudentService();
        List<Student> studentList = studentService.getStudents();
        Student student = new Student(4, "Ab");
        studentList.add(student);
        boolean actualResult = studentList.isEmpty();
//        assertFalse(actualResult);
//        assertFalse(actualResult, "Expected false but got " + actualResult);
//        assertFalse(()->actualResult);
        assertFalse(() -> actualResult, "Expected false but got " + actualResult);
    }
}