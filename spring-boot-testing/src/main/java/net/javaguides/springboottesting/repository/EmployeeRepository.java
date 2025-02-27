package net.javaguides.springboottesting.repository;

import net.javaguides.springboottesting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    // Define custom query using JPQL with index params
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByJPQL(String firstName, String lastName);

    // Define custom query using JPQL with named params
    @Query("select e from Employee e where e.firstName = :firstName and e.lastName = :lastName")
    Employee findByJPQLNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);

    // define custom query using Native SQL with index params
    @Query(value = "select * from employees e where e.first_name =?1 and e.last_name=?2", nativeQuery = true)
    Employee findByNativeSQL(String firstName, String lastName);

    // define custom query using Native SQL with named params
    @Query(value = "select * from employees e where e.first_name =:firstname and e.last_name=:lastname", nativeQuery = true)
    Employee findByNativeSQLNamed(@Param("firstname") String firstName, @Param("lastname") String lastName);
}
