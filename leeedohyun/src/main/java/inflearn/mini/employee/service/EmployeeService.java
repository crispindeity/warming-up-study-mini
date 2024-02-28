package inflearn.mini.employee.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inflearn.mini.employee.domain.Employee;
import inflearn.mini.employee.dto.request.EmployeeRegisterRequestDto;
import inflearn.mini.employee.repository.EmployeeRepository;
import inflearn.mini.team.domain.Team;
import inflearn.mini.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void registerEmployee(final EmployeeRegisterRequestDto request) {
        final Team team = teamRepository.findByName(request.teamName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));
        final Employee employee = request.toEntity();
        employee.joinTeam(team);
        employeeRepository.save(employee);
    }
}
