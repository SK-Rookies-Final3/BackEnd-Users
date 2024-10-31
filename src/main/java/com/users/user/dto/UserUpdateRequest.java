package com.users.user.dto;
import lombok.Data;
@Data
public class UserUpdateRequest {
    private String nickname;
    private String address;
    private Integer age;
    private String gender;
    private Integer height;
    private Integer weight;
    private String email;
}