package com.example.ec_mall.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
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
    @NotBlank(message = "E-mail을 입력하세요.")
    private String email;

    @NotBlank(message = "최대 10자로 생성하세요.")
    private String nickName;

    @NotBlank(message = "8 ~ 16자로 생성하세요. 대소문자, 특수문자, 숫자를 포함하여야 합니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[A-Za-z])(?=.*\\W)(?=\\S+$).{8,16}")
    private String password;

    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;

}
