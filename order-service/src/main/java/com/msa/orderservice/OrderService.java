package com.msa.orderservice;

import com.msa.orderservice.dto.OrderDto;
import com.msa.orderservice.request.RequestOrder;
import com.msa.orderservice.response.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public ResponseOrder createOrder(RequestOrder dto, Long userId) {
        Order order = Order.createOrder(dto, userId);
        Order savedOrder = orderRepository.save(order);

        return ResponseOrder.builder()
                .id(savedOrder.getId())
                .name(savedOrder.getName())
                .price(savedOrder.getPrice())
                .count(savedOrder.getCount())
                .totalPrice(savedOrder.getTotalPrice())
                .createdAt(savedOrder.getCreatedAt())
                .build();
    }

    public OrderDto getOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();

        return OrderDto.builder()
                //TODO name
                .price(order.getPrice())
                .count(order.getCount())
                .totalPrice(order.getTotalPrice())
                .build();
    }

    public List<ResponseOrder> getOrdersByUserId(Long userId) {

        return orderRepository.findByUserId(userId)
                .stream()
                .map(order -> ResponseOrder.builder()
                        .id(order.getId())
                        .name(order.getName())
                        .price(order.getPrice())
                        .count(order.getCount())
                        .totalPrice(order.getTotalPrice())
                        .createdAt(order.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
