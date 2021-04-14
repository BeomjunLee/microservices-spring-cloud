package com.msa.userservice;

import com.msa.userservice.dto.*;
import com.msa.userservice.dto.form.UserForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user-service")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final Environment env;
    private final UserService userService;

    @GetMapping("/status-check")
    public String status(HttpServletRequest request) {
        return "user-service 작동중 [PORT:" + request.getServerPort() + "]";
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
    public SignupDto createUser(@RequestBody UserForm form) {

        UserDto userDto = userService.createUser(form);

        return SignupDto.builder()
                .code(Code.SUCCESS)
                .status(HttpStatus.CREATED.value())
                .message("회원 가입 완료")
                .user(userDto)
                .build();
    }

    /**
     * 전체 회원 보기
     * @return Json Response
     */
    @GetMapping("/users")
    public ResponseEntity findUsers() {
        return ResponseEntity.ok(userService.getUserByAll());
    }

    /**
     * 회원 보기
     * @param userId 회원 pk id
     * @return Json Response
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity findUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

}
