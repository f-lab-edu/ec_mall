package com.example.ec_mall.controller;

import com.example.ec_mall.dao.OrderDao;
import com.example.ec_mall.dto.request.OrderRequestDTO;
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
     * @param id  productId
     * @param orderRequestDTO  사이즈, 수량
     * @param session  email
     * @return
     */
    @PostMapping("/{id}/orderSheet")
    public ResponseEntity<OrderDao> orderProduct(@PathVariable long id, @RequestBody @Valid OrderRequestDTO orderRequestDTO, HttpSession session) {
        orderService.order(session.getAttribute("account").toString(), orderRequestDTO, id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
