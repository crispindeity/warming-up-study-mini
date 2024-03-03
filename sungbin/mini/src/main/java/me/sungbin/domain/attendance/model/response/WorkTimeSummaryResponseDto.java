package me.sungbin.domain.attendance.model.response;

import java.util.List;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.attendance.model.response
 * @fileName : WorkTimeSummary
 * @date : 3/3/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/3/24       rovert         최초 생성
 */
public record WorkTimeSummaryResponseDto(List<WorkTimeDetail> details, long sum) {
}
