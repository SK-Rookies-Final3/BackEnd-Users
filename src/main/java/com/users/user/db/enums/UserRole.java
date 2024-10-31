package com.users.user.db.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
    CLIENT("고객"),
    OWNER("사업관리자"),
    MASTER("개발관리자")
    ;

    private String description;
}