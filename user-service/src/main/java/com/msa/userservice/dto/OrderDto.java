package com.msa.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class OrderDto {

    private Long productId;
    private int quantity;
    private int unitPrice;
    private int totalPrice;
    private LocalDateTime createAt;

    private Long orderId;

}
