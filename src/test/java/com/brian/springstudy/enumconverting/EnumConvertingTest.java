package com.brian.springstudy.enumconverting;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EnumConvertingTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 파라미터에 @RequestParam 정의된 메서드 테스트
     *
     * RequestParamMethodArgumentResolver 동작
     *  GenericConversionService 내에서 파라미터 타입에 맞는 converter 가 컨버팅
     *  (직접 등록한 StringToEnumConverterFactory 가 실행됨을 확인할 수 있다.)
     */
    @Test
    void Request_with_QueryParameter_Test() throws Exception {
        mvc.perform(post("/user/enum")
                .contentType(MediaType.APPLICATION_JSON)
                .param("deleteYn", "Y"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * 파라미터에 @RequestBody 정의된 메서드 테스트
     *
     * RequestResponseBodyMethodProcessor 동작
     *  HttpMessageConverter 를 상속한 converter 중에 파라미터 타입에 맞는 converter 가 컨버팅
     *  (Jackson 컨버터 - MappingJackson2HttpMessageConverter 가 실행됨을 확인할 수 있다.)
     */
    @Test
    void Request_with_Body_Test() throws Exception {
        Map user = new HashMap();
        user.put("userGrade", "G");
        user.put("deleteYn", "Y");

        mvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * @ResponseBody 정의된 메서드 테스트
     */
    @Test
    void Response_with_Dto_Test() throws Exception {
        mvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.deleteYn", is("N")))
                .andExpect(jsonPath("$.userGrade", is("G")))
                .andDo(print());
    }

    /**
     * Enum <-> DB converting 테스트
     */
    @Test
    void JPA_Converting_Test() throws Exception {
        // insert
        // enum -> db
        String content = makeContent();

        MvcResult mvcResult = mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andReturn();

        // select
        // db -> enum
        int userId = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Integer.class);

        mvc.perform(get("/user/"+userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    String makeContent() throws Exception {
        Map user = new HashMap();
        user.put("userGrade", "N");
        user.put("deleteYn", "Y");

        return objectMapper.writeValueAsString(user);
    }
}
