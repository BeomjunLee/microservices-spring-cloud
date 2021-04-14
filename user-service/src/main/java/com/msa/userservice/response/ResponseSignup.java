package com.msa.userservice.response;

import com.msa.userservice.dto.Code;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseSignup {

    private Code code;
    private int status;
    private String message;
    private ResponseUser user;

}
