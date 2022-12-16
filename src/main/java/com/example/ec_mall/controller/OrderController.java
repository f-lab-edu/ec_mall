package com.example.ec_mall.controller;

import com.example.ec_mall.dto.request.OrderRequestDTO;
import com.example.ec_mall.dto.response.OrderResponseDTO;
import com.example.ec_mall.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    /**
     * @param orderRequestDTO  사이즈, 수량
     * @param session  email
     * @return
     */
    @PostMapping
    public ResponseEntity<OrderResponseDTO> orderProduct(@RequestBody @Valid OrderRequestDTO orderRequestDTO, HttpSession session) {
        orderService.order(session.getAttribute("account").toString(), orderRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
