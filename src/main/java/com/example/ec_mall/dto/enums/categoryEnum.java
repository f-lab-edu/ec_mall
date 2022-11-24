package com.example.ec_mall.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum categoryEnum {
    ROOT("카테고리", null),
        TOP("상의", ROOT),
            T_SHIRT("티셔츠", TOP),
            HOOD("후드", TOP),
            SHIRT("셔츠", TOP),
        PANTS("하의", ROOT),
            JEAN("청바지", PANTS),
            HALF("반바지", PANTS);
    // 카테고리 이름
    private String title;
    // 부모 카테고리
    private categoryEnum parentCategory;
    // 자식카테고리
    private List<categoryEnum> childCategories;


    public String getTitle() {
        return title;
    }

    // 부모카테고리 Getter
    public Optional<categoryEnum> getParentCategory() {
        return Optional.ofNullable(parentCategory);
    }

    // 자식카테고리 Getter
    public List<categoryEnum> getChildCategories() {
        return Collections.unmodifiableList(childCategories);
    }

    categoryEnum(String title, categoryEnum parentCategory) {
        this.childCategories = new ArrayList<>();
        this.title = title;
        this.parentCategory = parentCategory;
        if(Objects.nonNull(parentCategory)) parentCategory.childCategories.add(this);
    }
}

