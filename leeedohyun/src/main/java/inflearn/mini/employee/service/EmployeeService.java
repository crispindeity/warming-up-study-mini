package inflearn.mini.employee.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inflearn.mini.employee.dto.request.EmployeeRegisterRequestDto;
import inflearn.mini.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional
    public void registerEmployee(final EmployeeRegisterRequestDto request) {
        employeeRepository.save(request.toEntity());
    }
}
