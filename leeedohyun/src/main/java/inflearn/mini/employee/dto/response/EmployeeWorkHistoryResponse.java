package inflearn.mini.employee.dto.response;

import java.util.List;

public record EmployeeWorkHistoryResponse(List<DateWorkMinutes> detail, long sum) {
}
