package com.msa.userservice;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.userservice.form.UserForm;
import com.msa.userservice.response.ResponseUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserService userService;

    @Test
    @DisplayName("회원 가입 성공")
    public void signUp() throws Exception {
        //given
        UserForm user = UserForm.builder()
                .username("test")
                .password("1234")
                .name("테스트유저")
                .build();
        //when
        ResultActions resultActions = requestSignUp(user);
        //then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("code").value("SUCCESS"))
                .andExpect(jsonPath("message").value("회원 가입 성공"))
                .andExpect(jsonPath("user.id").exists())
                .andExpect(jsonPath("user.username").value(user.getUsername()))
                .andExpect(jsonPath("user.name").value(user.getName()));
    }

    @Test
    @DisplayName("회원 가입 실패(아이디 중복)")
    public void signUpDuplicateUsername() throws Exception {
        //given
        UserForm user = UserForm.builder()
                .username("test")
                .password("1234")
                .name("테스트유저")
                .build();
        userService.createUser(user);
        //when
        ResultActions resultActions = requestSignUp(user);
        //then
        resultActions
                .andExpect(status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UsernameNotFoundException))
                .andExpect(jsonPath("code").value("FAIL"))
                .andExpect(jsonPath("status").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("message").value("["+ user.getUsername() + "] 중복된 아이디 입니다"));
    }

    @Test
    @DisplayName("회원 조회 테스트")
    public void findUser() throws Exception {
        //given
        UserForm user = UserForm.builder()
                .username("test")
                .password("1234")
                .name("테스트유저")
                .build();
        ResponseUser savedUser = userService.createUser(user);
        //when
        ResultActions resultActions = requestFindUser(savedUser.getId());
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(savedUser.getId()))
                .andExpect(jsonPath("username").value(savedUser.getUsername()))
                .andExpect(jsonPath("name").value(savedUser.getName()))
                .andExpect(jsonPath("createdAt").value(savedUser.getCreatedAt().toString()));
    }

    @Test
    @DisplayName("전체 회원 조회 테스트")
    public void findUsers() throws Exception {
        //given
        UserForm user0 = UserForm.builder()
                .username("test1")
                .password("1234")
                .name("테스트유저1")
                .build();
        UserForm user1 = UserForm.builder()
                .username("test2")
                .password("1234")
                .name("테스트유저2")
                .build();

        ResponseUser savedUser0 = userService.createUser(user0);
        ResponseUser savedUser1 = userService.createUser(user1);

        //when
        ResultActions resultActions = requestFindUsers();
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id").value(savedUser0.getId()))
                .andExpect(jsonPath("[0].username").value(savedUser0.getUsername()))
                .andExpect(jsonPath("[0].name").value(savedUser0.getName()))
                .andExpect(jsonPath("[0].createdAt").exists())
                .andExpect(jsonPath("[1].id").value(savedUser1.getId()))
                .andExpect(jsonPath("[1].username").value(savedUser1.getUsername()))
                .andExpect(jsonPath("[1].name").value(savedUser1.getName()))
                .andExpect(jsonPath("[1].createdAt").exists());
    }

    /**
     * 회원 가입 요청
     * @param form 회원 가입 form
     */
    private ResultActions requestSignUp(UserForm form) throws Exception {
        return mockMvc.perform(post("/user-service/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andDo(print());
    }

    /**
     * 회원 조회 요청
     * @param userId 회원 pk id
     */
    private ResultActions requestFindUser(Long userId) throws Exception {
        return mockMvc.perform(get("/user-service/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    /**
     * 전체 회원 조회 요청
     */
    private ResultActions requestFindUsers() throws Exception {
        return mockMvc.perform(get("/user-service/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}