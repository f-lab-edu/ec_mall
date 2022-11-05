package com.example.ec_mall.mapper;

import com.example.ec_mall.dto.MemberRequestDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    int signUpMember(MemberRequestDTO memberRequestDTO);
    int emailCheck(String email);

}
