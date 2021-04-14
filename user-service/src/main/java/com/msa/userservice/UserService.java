package com.msa.userservice;

import com.msa.userservice.response.ResponseUser;
import com.msa.userservice.dto.form.UserForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 저장
     * @param form 회원가입 폼
     * @return UserDto
     */
    public ResponseUser createUser(UserForm form) {
        duplicateUsername(form.getUsername());

        User user = User.builder()
                .username(form.getUsername())
                .password(passwordEncoder.encode(form.getPassword()))
                .name(form.getName())
                .build();


        User savedUser = userRepository.save(user);

        return ResponseUser.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .name(savedUser.getName())
                .createdAt(savedUser.getCreatedAt())
                .build();
    }

    /**
     * 회원 아이디 중복검사
     * @param username 회원 아이디
     */
    private void duplicateUsername(String username) {
        if(userRepository.findByUsername(username) > 0) {
            log.error("아이디 중복");
            throw new UsernameNotFoundException(username + " 은 중복된 아이디 입니다");
        }
    }

    /**
     * 전체 회원 찾기
     * @return
     */
    public List<ResponseUser> getUserByAll() {
        return userRepository.findAll().stream().map(user -> ResponseUser.builder()
                                                                .id(user.getId())
                                                                .username(user.getUsername())
                                                                .name(user.getName())
                                                                .createdAt(user.getCreatedAt())
                                                                .build()).collect(Collectors.toList());
    }

    /**
     * 회원 찾기
     * @param id 회원 pk id
     * @return UserDto
     */
    public ResponseUser getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다"));

        return ResponseUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
