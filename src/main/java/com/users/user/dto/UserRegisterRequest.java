package com.users.user.dto;
import com.users.user.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private UserRole role;
}