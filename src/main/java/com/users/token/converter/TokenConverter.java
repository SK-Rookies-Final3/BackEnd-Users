package com.users.token.converter;

import com.users.common.annotation.Converter;
import com.users.common.error.ErrorCode;
import com.users.common.exception.ApiException;
import com.users.token.controller.model.TokenResponse;
import com.users.token.dto.TokenDto;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Converter
public class TokenConverter {
    // converter는 mapper와 유사함
    // 그러나 mapper: 객체 간의 변환 처리
    // converter: 간단한 데이터 타입 간의 변환

    public TokenResponse toResponse(
            TokenDto accessToken,
            TokenDto refreshToken,
            String role,
            int id
    ) {
        Objects.requireNonNull(accessToken, () -> {
            throw new ApiException(ErrorCode.NULL_POINT);
        });
        Objects.requireNonNull(refreshToken, () -> {
            throw new ApiException(ErrorCode.NULL_POINT);
        });

        return TokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessTokenExpiredAt(accessToken.getExpiredAt())
                .refreshToken(refreshToken.getToken())
                .role(role)
                .id(id)
                .build();
    }
}