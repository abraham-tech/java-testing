package net.javaguides.springboottesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.springboottesting.model.Employee;
import net.javaguides.springboottesting.service.EmployeeService;
import net.javaguides.springboottesting.service.impl.EmployeeServiceImpl;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class EmployeeControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeServiceImpl employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnEmployee() throws Exception {
        // given
        Employee employee = Employee.builder()
                .firstName("Abraham")
                .lastName("Meja")
                .email("abraham@gmail.com")
                .build();

        given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // when
        ResultActions response = mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee))
        );

        // then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Abraham"))
                .andExpect(jsonPath("$.lastName").value("Meja"))
                .andExpect(jsonPath("$.email").value("abraham@gmail.com"));
    }

    @Test
    public void givenListOfEmployee_whenGetAllEmployees_thenReturnEmployeeList() throws Exception {
        // given - precondition or set up
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder().firstName("Abraham").lastName("Meja").email("abraham@gmail.com").build());
        listOfEmployees.add(Employee.builder().firstName("Abel").lastName("Meja").email("abel@gmail.com").build());

        given(employeeService.getAllEmployees()).willReturn(listOfEmployees);
        // when
        ResultActions response = mockMvc.perform(get("/api/v1/employees"));

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.size()",
                                CoreMatchers.is(listOfEmployees.size())));
    }

    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee() throws Exception {
        // give
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .id(employeeId)
                .firstName("Abraham")
                .lastName("Meja")
                .email("abraham@gmail.com")
                .build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

        // when
        ResultActions response = mockMvc.perform(get("/api/v1/employees/" + employeeId));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName").value("Abraham"))
                .andExpect(jsonPath("$.lastName").value("Meja"))
                .andExpect(jsonPath("$.email").value("abraham@gmail.com"));
    }

    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {
        // give
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .id(employeeId)
                .firstName("Abraham")
                .lastName("Meja")
                .email("abraham@gmail.com")
                .build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        // when
        ResultActions response = mockMvc.perform(get("/api/v1/employees/" + employeeId));

        // then
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    public void givenUpdateEmployee_whenUpdateEmployee_thenReturnEmployee() throws Exception {
        // give
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("Abraham")
                .lastName("Meja")
                .email("meja@gmail.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Updated Abraham")
                .lastName("Updated Meja")
                .email("updatedMeja@gmail.com")
                .build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedEmployee));
        given(employeeService.updateEmployee(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));
        // when
        ResultActions response = mockMvc.perform(put("/api/v1/employees/" + employeeId)
        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEmployee))
        );

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated Abraham"))
                .andExpect(jsonPath("$.lastName").value("Updated Meja"))
                .andExpect(jsonPath("$.email").value("updatedMeja@gmail.com"));
    }

    @Test
    public void givenUpdateEmployee_whenUpdateEmployee_thenReturn404() throws Exception {
        // give
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("Abraham")
                .lastName("Meja")
                .email("meja@gmail.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Updated Abraham")
                .lastName("Updated Meja")
                .email("updatedMeja@gmail.com")
                .build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
        given(employeeService.updateEmployee(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));
        // when
        ResultActions response = mockMvc.perform(put("/api/v1/employees/" + employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee))
        );

        // then
        response.andExpect(status().isNotFound());
    }
}
