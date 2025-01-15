package net.javaguides.student;

import net.javaguides.student.entity.Student;
import net.javaguides.student.repository.StudentRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class StudentApplicationTests {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void givenStudents_whenGetAllStudents_thenReturnAllStudents() throws Exception {
		// given
		List<Student> students = List.of(
				new Student( "Abraham","Meja","meja@gmail.com"),
				new Student( "Abraham","Meja","meja@gmail.com")
		);
		studentRepository.saveAll(students);

		// when
		ResultActions response =  mockMvc.perform(MockMvcRequestBuilders.get("/api/studetns"));

		// then
		response.andExpect(MockMvcResultMatchers.status().isOk());
		response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(students.size())));
	}

}
