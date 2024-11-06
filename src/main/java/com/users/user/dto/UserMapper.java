package com.users.user.dto;
import com.users.common.annotation.Converter;
import com.users.common.error.ErrorCode;
import com.users.common.exception.ApiException;
import com.users.user.db.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Converter
public class UserMapper {
    public User toEntity(UserRegisterRequest request) {
        // Request 데이터 유효성 검사 추가
        if (request == null) {
            throw new ApiException(ErrorCode.NULL_POINT, "UserRegisterRequest Null");
        }

        // 엔티티로 변환
        return User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .role(request.getRole())
                .build();
    }

    public User toEntity(UserUpdateRequest request) {
        // Request 데이터 유효성 검사 추가
        if (request == null) {
            throw new ApiException(ErrorCode.NULL_POINT, "UserRegisterRequest Null");
        }

        // 엔티티로 변환
        return User.builder()
                .nickname(request.getNickname())
                .address(request.getAddress())
                .age(request.getAge())
                .gender(request.getGender())
                .height(request.getHeight())
                .weight(request.getWeight())
                .email(request.getEmail())
                .build();
    }

    public UserResponse toResponse(User user) {
        return Optional.ofNullable(user)
                .map(it -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .nickname(user.getNickname())
                        .address(user.getAddress())
                        .age(user.getAge())
                        .gender(user.getGender())
                        .height(user.getHeight())
                        .weight(user.getWeight())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .createdAt(user.getCreatedAt())
                        .lastAccpetedAt(user.getLastAccpetedAt())
                        .build())
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserEntity Null"));
    }

    public UserResponse toResponse(UserResponse updatedEntity) {
        return Optional.ofNullable(updatedEntity)
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .nickname(user.getNickname())
                        .address(user.getAddress())
                        .age(user.getAge())
                        .gender(user.getGender())
                        .height(user.getHeight())
                        .weight(user.getWeight())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .createdAt(user.getCreatedAt())
                        .lastAccpetedAt(user.getLastAccpetedAt())
                        .build())
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserEntity Null"));
    }
}