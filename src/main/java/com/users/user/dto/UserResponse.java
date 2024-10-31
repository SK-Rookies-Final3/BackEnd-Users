package com.users.user.dto;

import com.users.user.db.enums.UserRole;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String address;
    private int age;
    private String gender;
    private int height;
    private int weight;
    private String email;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime lastAccpetedAt;
}
