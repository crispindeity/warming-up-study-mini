package me.sungbin.global.config.audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * @author : rovert
 * @packageName : me.sungbin.global.config.audit
 * @fileName : AuditorAwareImpl
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("tester");
    }
}
