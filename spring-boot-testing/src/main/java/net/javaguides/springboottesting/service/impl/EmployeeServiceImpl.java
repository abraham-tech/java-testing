package net.javaguides.springboottesting.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboottesting.exception.ResourceNotFoundException;
import net.javaguides.springboottesting.model.Employee;
import net.javaguides.springboottesting.repository.EmployeeRepository;
import net.javaguides.springboottesting.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());

        if (savedEmployee.isPresent()) {
            throw new ResourceNotFoundException("Employee with email " + employee.getEmail() + " already exists");
        }


        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
