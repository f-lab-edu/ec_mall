package com.example.ec_mall.mapper;

import com.example.ec_mall.dao.MemberDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    int signUpMember(MemberDao memberDao);
    int emailCheck(String email);

}
