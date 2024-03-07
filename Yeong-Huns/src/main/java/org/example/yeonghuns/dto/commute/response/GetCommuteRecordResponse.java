package org.example.yeonghuns.dto.commute.response;

import java.util.List;

/**
 * packageName    : org.example.yeonghuns.dto.commute.response
 * fileName       : GetCommuteRecordResponse
 * author         : Yeong-Huns
 * date           : 2024-03-05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-05        Yeong-Huns       최초 생성
 */
public record GetCommuteRecordResponse(List<GetCommuteDetail> detail, long sum) {
}
