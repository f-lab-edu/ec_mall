package com.example.ec_mall.mapper;

import com.example.ec_mall.dao.MemberDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Mapper
 * - Marker interface for MyBatis mappers.
 * - 기능이 있는게 아니라, 무언가 표시(마커)를 하기 위한 인터페이스를 의미.
 * - 즉, 단순히 이건 매퍼입니다. 라는 것을 표시하기 위한 어노테이션
 * - Mapper : SQL문을 메소드(java)로 매핑 시켜주는 것을 의미.
 *
 * @Repository
 * - @Mapper 와 차이는 @Component가 붙어있다. streotype으로 스프링에서 빈 등록해서 관리.
 * - value 라는 값을 받는데, 이는 mapper.xml의 namespace를 repository 메소드명과 각 id에 매칭 시킬때 유용하다.
 * - Repository : Mapper를 포함하고 있는 개념, DB와 연결하는 목적이 강한 Mapper와 달리 연결 뿐 아니라 데이터를 조작(저장,검색,업데이트,삭제)하는 것도 추가. *
 */
@Mapper
public interface MemberMapper {
    int signUpMember(MemberDao memberDao);
    int emailCheck(String email);
    MemberDao findByEmailPassword(String email, String password);
}
