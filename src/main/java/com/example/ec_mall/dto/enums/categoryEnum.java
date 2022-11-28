package com.example.ec_mall.dto.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum categoryEnum {
    TOP("Short-Top","Long-Top"),
    PANTS("Short-Pants","Long-Pants");
    /**
     * Enum은 불변의 성격을 가지기 때문에 멤버 변수는 final로 정의하여 사용해야 한다.
     * 모든 인자들이 static final로 정의 되기 때문에 thread간 공유가 가능하여
     * 멤버 변수를 final로 설정하지 않으면 의도하지 않게 변경된 값을 사용할 가능성이 존재하기 때문.
     */
    private final String Short;
    private final String Long;
}