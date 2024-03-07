package com.miniproject.commute.dto.commute.request;

import jakarta.validation.constraints.NotNull;

public record WorkOutRequest(@NotNull(message = "직원의 id 값을 필수로 입력해야 합니다.") long memberId){

}
