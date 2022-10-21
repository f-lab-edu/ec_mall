package com.example.ec_mall.mapper;

import com.example.ec_mall.dto.TestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface TestMapper {
    List<TestDTO> getTestData();
}
