package net.javaguides.springboottesting.service;

import net.javaguides.springboottesting.model.Employee;
import net.javaguides.springboottesting.repository.EmployeeRepository;
import net.javaguides.springboottesting.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.hibernate.query.sqm.mutation.internal.cte.CteInsertStrategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setUp() {
//        employeeRepository = Mockito.mock(EmployeeRepository.class);
//        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    // JUnit test for save Emplopyee method
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){
        // given - precondition or setup
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Abraham")
                .lastName("Meja")
                .email("abraham@gmail.com")
                .build();

        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        // when - action or the behavior that we are going test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then - verify the output
        Assertions.assertThat(savedEmployee.getId()).isEqualTo(employee.getId());
        Assertions.assertThat(savedEmployee).isNotNull();

    }
}
