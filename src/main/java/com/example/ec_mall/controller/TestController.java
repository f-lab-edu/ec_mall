package com.example.ec_mall.controller;

import com.example.ec_mall.dto.TestDTO;
import com.example.ec_mall.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping("/Test")
    public List<TestDTO> HelloWorld() {
        return testService.getTestData();
    }
}
