package me.sungbin.domain.attendance.model.response;

import java.time.LocalDate;

/**
 * @author : rovert
 * @packageName : me.sungbin.domain.attendance.model.response
 * @fileName : WorkTimeDetail
 * @date : 3/3/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/3/24       rovert         최초 생성
 */

public record WorkTimeDetail(LocalDate date, long workingMinutes, boolean usingDayOff) {
}
