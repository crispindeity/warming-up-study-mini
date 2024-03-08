package inflearn.mini.employee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import inflearn.mini.employee.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findById(final Long id);
}
