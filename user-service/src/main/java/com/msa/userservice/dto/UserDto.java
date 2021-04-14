package com.msa.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) //해당 property 가 null 일 때 json 출력 x 
public class UserDto {

    private Long id;
    private String username;
    private String name;
    private LocalDateTime createdAt;

    private List<OrderDto> orders;
}
