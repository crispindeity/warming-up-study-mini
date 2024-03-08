package org.example.yeonghuns.domain;

import lombok.Getter;

/**
 * packageName    : org.example.yeonghuns.domain
 * fileName       : WorkStartDay
 * author         : Yeong-Huns
 * date           : 2024-03-08
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-08        Yeong-Huns       최초 생성
 */

public enum JoinDate {
    OVER_ONE_YEAR(15L),
    UNDER_ONE_YEAR(11L);

    private final long maxAnnualLeaves;
    public long getAnnualLeaves(){return maxAnnualLeaves;}
    JoinDate(long maxAnnualLeaves) {
        this.maxAnnualLeaves = maxAnnualLeaves;
    }
}
