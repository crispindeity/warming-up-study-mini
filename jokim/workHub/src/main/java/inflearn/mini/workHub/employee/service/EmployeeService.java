package inflearn.mini.workHub.employee.service;

import inflearn.mini.workHub.employee.domain.Employee;
import inflearn.mini.workHub.employee.dto.EmployeeInfoResponse;
import inflearn.mini.workHub.employee.dto.EmployeeRegisterRequest;
import inflearn.mini.workHub.employee.repository.EmployeeRepository;
import inflearn.mini.workHub.team.domain.Team;
import inflearn.mini.workHub.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final TeamRepository teamRepository;
    @Transactional
    public void registerEmployee(EmployeeRegisterRequest request) {
        Employee employee = request.toEntity();
        Team team = teamRepository.findByName(request.teamName()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));

        if (employee.isManagerYn() && team.existManager()) {
            throw new IllegalArgumentException("이미 매니저가 존재합니다.");
        }

        employee.joinTeam(team);
        employeeRepository.save(employee);

    }

    public List<EmployeeInfoResponse> getEmployeeInfoList() {
        List<Employee> infoList = employeeRepository.findAll();

        return infoList.stream()
                .map(EmployeeInfoResponse::from)
                .toList();
    }
}
