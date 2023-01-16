package com.example.ec_mall.controller;

import com.example.ec_mall.dto.request.OrderRequestDTO;
import com.example.ec_mall.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    /**
     * @param items  여러건에 대한 상품 ID, 사이즈, 수량
     * @param email  email
     * @return
     */
    @PostMapping
    public ResponseEntity<OrderRequestDTO> orderProduct(@Valid String email,@RequestBody List<OrderRequestDTO> items) {
        orderService.order(email, items);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
