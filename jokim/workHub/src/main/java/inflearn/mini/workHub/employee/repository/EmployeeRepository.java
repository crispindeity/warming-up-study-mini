package inflearn.mini.workHub.employee.repository;

import inflearn.mini.workHub.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

}
