package com.example.ec_mall.mapper;

import com.example.ec_mall.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    public int regMember(MemberDTO memberDTO);

    int idCheck(String email);

}
