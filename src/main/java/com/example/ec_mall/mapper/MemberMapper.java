package com.example.ec_mall.mapper;

import com.example.ec_mall.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    int regMember(MemberDTO memberDTO);
    int emailCheck(String email);

}
