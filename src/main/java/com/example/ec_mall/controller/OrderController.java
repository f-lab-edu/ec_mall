package com.example.ec_mall.controller;

import com.example.ec_mall.dto.request.OrderRequestDTO;
import com.example.ec_mall.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    /**
     * @param items  여러건에 대한 상품 ID, 사이즈, 수량
     * @param session  email
     * @return
     */
    @PostMapping
    public ResponseEntity<List<OrderRequestDTO>> orderProduct(@RequestBody @Valid List<OrderRequestDTO> items, HttpSession session) {
        orderService.order(session.getAttribute("account").toString(), items);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
