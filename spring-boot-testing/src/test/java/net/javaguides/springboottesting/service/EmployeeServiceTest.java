package net.javaguides.springboottesting.service;

import net.javaguides.springboottesting.exception.ResourceNotFoundException;
import net.javaguides.springboottesting.model.Employee;
import net.javaguides.springboottesting.repository.EmployeeRepository;
import net.javaguides.springboottesting.service.impl.EmployeeServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;
import org.hibernate.query.sqm.mutation.internal.cte.CteInsertStrategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setUp() {
//        employeeRepository = Mockito.mock(EmployeeRepository.class);
//        employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .id(1L)
                .firstName("Abraham")
                .lastName("Meja")
                .email("abraham@gmail.com")
                .build();
    }

    // JUnit test for save Emplopyee method
    @DisplayName("JUnit test for saveEmployee method return new employee")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){
        // given - precondition or setup

        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        // when - action or the behavior that we are going test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then - verify the output
        assertThat(savedEmployee.getId()).isEqualTo(employee.getId());
        assertThat(savedEmployee).isNotNull();

    }


    @DisplayName("JUnit test for save employee which return exception for existing employee")
    @Test
    public void givenEmployeeObject_whenSaveExistingEmployee_thenReturnException(){
        // given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));


        // When - action or the behavior that we are going to test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () ->
                employeeService.saveEmployee(employee));

        // then - verify the output
        verify(employeeRepository, never()).save(any(Employee.class));

    }

    @DisplayName("JUnit test for getAllEmployees method")
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeeList(){
        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(1L)
                .firstName("Leuel")
                .lastName("Leuel")
                .email("leuel@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));
        // when - action or the behavior that we are going to test
        List<Employee> employees = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(2);
    }


    @DisplayName("JUnit test for getAllEmployees method (negative scenario)")
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeeList(){
        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(1L)
                .firstName("Leuel")
                .lastName("Leuel")
                .email("leuel@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());
        // when - action or the behavior that we are going to test
        List<Employee> employees = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employees).isEmpty();
        assertThat(employees.size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getEmployeeById method")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee(){
        // given
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // when

        Optional<Employee> savedEmployee = employeeService.getEmployeeById(employee.getId());
        // then

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.get().getId()).isEqualTo(employee.getId());
    }

    @DisplayName("JUnit for updateEmployee method")
    @Test
    public void givenEmployee_whenUpdateEmployee_thenReturnEmployeeObject(){
        // given
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("John@gmail.com");
        employee.setFirstName("John");
        employee.setLastName("Doe");

        // when
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        // then
        assertThat(updatedEmployee.getId()).isEqualTo(employee.getId());
        assertThat(updatedEmployee.getFirstName()).isEqualTo(employee.getFirstName());
        assertThat(updatedEmployee.getLastName()).isEqualTo(employee.getLastName());
    }

    @DisplayName("JUnit for deleteEmployee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturnVoid(){
        // given
        willDoNothing().given(employeeRepository).deleteById(employee.getId());

        // when
        employeeService.deleteEmployee(employee.getId());

        // then
        verify(employeeRepository, times(1)).deleteById(employee.getId());
    }

}
