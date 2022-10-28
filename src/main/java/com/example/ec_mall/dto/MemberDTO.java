package com.example.ec_mall.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDTO {

    /**
     * @NotNull : null 허용하지 않음.
     * @NotBlank : null 허용하지 않으면서, 공백문자가 아닌 문자가 한개 이상 포함되어야 함.
     */
    @NotNull
    private Long id;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String nickName;

    @NotBlank
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
