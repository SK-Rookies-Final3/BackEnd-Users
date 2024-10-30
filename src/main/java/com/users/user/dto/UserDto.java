package com.users.user.dto;

import com.users.user.enums.UserRole;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class UserDto {

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
    private LocalDateTime createdat;
    private LocalDateTime lastAccpetedat;
}