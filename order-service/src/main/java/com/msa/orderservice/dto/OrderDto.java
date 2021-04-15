package com.msa.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OrderDto {

    private String name;
    private int price;
    private int count;
    private int totalPrice;

    private Long productId;
    private Long userId;
}
