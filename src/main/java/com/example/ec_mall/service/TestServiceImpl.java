package com.example.ec_mall.service;

import com.example.ec_mall.dto.TestDTO;
import com.example.ec_mall.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestMapper testMapper;

    @Override
    public List<TestDTO> getTestData(){

        return testMapper.getTestData();
    }
}
