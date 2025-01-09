package net.javaguides.springboottesting.service;

import net.javaguides.springboottesting.exception.ResourceNotFoundException;
import net.javaguides.springboottesting.model.Employee;
import net.javaguides.springboottesting.repository.EmployeeRepository;
import net.javaguides.springboottesting.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.hibernate.query.sqm.mutation.internal.cte.CteInsertStrategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

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
        Assertions.assertThat(savedEmployee.getId()).isEqualTo(employee.getId());
        Assertions.assertThat(savedEmployee).isNotNull();

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
        Assertions.assertThat(employees).isNotNull();
        Assertions.assertThat(employees.size()).isEqualTo(2);
    }

}
