package com.msa.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String name;
    private LocalDateTime createdAt;
}
