package inflearn.mini.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inflearn.mini.employee.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
