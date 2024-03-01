package me.sungbin.global.common.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.sungbin.global.common.annotation.IntegrationTest;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author : rovert
 * @packageName : me.sungbin.global.common.controller
 * @fileName : BaseControllerTest
 * @date : 3/1/24
 * @description :
 * ===========================================================
 * DATE 			AUTHOR			 NOTE
 * -----------------------------------------------------------
 * 3/1/24       rovert         최초 생성
 */

@Disabled
@IntegrationTest
public class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
}
