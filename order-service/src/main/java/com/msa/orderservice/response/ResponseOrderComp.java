package com.msa.orderservice.response;
import com.msa.orderservice.response.enums.Code;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class ResponseOrderComp {

    private Code code;
    private int status;
    private String message;
    private ResponseOrder order;
}
