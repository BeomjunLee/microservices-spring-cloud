package com.msa.userservice;

import com.msa.userservice.dto.*;
import com.msa.userservice.dto.form.UserForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-service")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final Environment env;
    private final UserService userService;

    @GetMapping("/status-check")
    public String status() {
        return "user-service 작동중";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return env.getProperty("greeting.message");
    }

    /**
     * 회원 가입
     * @param form 회원가입 form
     * @return Json Response
     */
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseSignup createUser(@RequestBody UserForm form) {

        UserDto userDto = userService.saveUser(form);

        return ResponseSignup.builder()
                .code(Code.SUCCESS)
                .status(HttpStatus.CREATED.value())
                .message("회원 가입 완료")
                .userDto(userDto)
                .build();
    }
}
