package com.example.ec_mall.dto.request;

import lombok.*;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequestDTO {

    /**
     * @NotNull : null 허용하지 않음.
     * @NotBlank : null 허용하지 않으면서, 공백문자가 아닌 문자가 한개 이상 포함되어야 함.(null, "", " " 모두 허용하지 않음)
     * @NotEmpty : null과 "" 허용하지 않지만, " "와 같이 공백문자는 허용된다.
     * @Email : Email 형식 검증(Null을 허용하기 때문에 @NotNull과 같은 어노테이션과 같이 사용), @ 표시가 있는지만 검증하므로 특수문자등의 제한을 두려면 Pattern을 사용해 정규식으로 표현하는게 좋다.
     *          특히나 이 프로젝트에선 Email을 아이디로 사용하기 때문에 더욱 정규식을 써야한다.
     */

    @NotBlank(message = "E-mail을 입력하세요.")
    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일 형식에 맞게 입력하세요.")
    private String email;

    @NotBlank(message = "NickName을 입력하세요.")
    @Size(min = 2, max = 10, message = "최소 2자, 최대 10자로 생성하세요")
    private String nickName;

    @NotBlank(message = "비밀번호를 입력하세요")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[A-Za-z])(?=.*\\W)(?=\\S+$).{8,16}", message = "8 ~ 16자로 생성하세요. 대소문자, 특수문자, 숫자를 포함하여야 합니다.")
    private String password;

}
