package com.msa.userservice;

import com.msa.userservice.dto.UserDto;
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
    public UserDto saveUser(UserForm form) {
        User user = User.builder()
                .username(form.getUsername())
                .password(passwordEncoder.encode(form.getPassword()))
                .name(form.getName())
                .build();

        duplicateUsername(form.getUsername());

        User savedUser = userRepository.save(user);

        return UserDto.builder()
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
    public List<UserDto> findUsers() {
        return userRepository.findAll().stream().map(user -> UserDto.builder()
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
    public UserDto findUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다"));

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
