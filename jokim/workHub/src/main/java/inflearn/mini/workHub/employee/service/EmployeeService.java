package inflearn.mini.workHub.employee.service;

import inflearn.mini.workHub.employee.domain.Employee;
import inflearn.mini.workHub.employee.dto.EmployeeInfoResponse;
import inflearn.mini.workHub.employee.dto.EmployeeRegisterRequest;
import inflearn.mini.workHub.employee.repository.EmployeeRepository;
import inflearn.mini.workHub.global.CustomException;
import inflearn.mini.workHub.team.domain.Team;
import inflearn.mini.workHub.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static inflearn.mini.workHub.global.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final TeamRepository teamRepository;
    @Transactional
    public void registerEmployee(EmployeeRegisterRequest request) {
        Employee employee = request.toEntity();
        Team team = teamRepository.findByName(request.teamName())
                .orElseThrow(() -> new CustomException(NOT_EXIST_TEAM));

        if (employee.isManagerYn() && team.existManager()) {
            throw new CustomException(EXIST_MANAGER);
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
