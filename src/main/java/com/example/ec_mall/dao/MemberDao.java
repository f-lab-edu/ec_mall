package com.example.ec_mall.dao;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class MemberDao {
    private Long id;
    private String email;
    private String nickName;
    private String password;
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;
}
