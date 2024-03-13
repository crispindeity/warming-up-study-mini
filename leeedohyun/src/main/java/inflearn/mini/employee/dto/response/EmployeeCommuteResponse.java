package inflearn.mini.employee.dto.response;

import java.util.List;

public record EmployeeCommuteResponse(List<DateWorkMinutes> detail, long sum) {
}
