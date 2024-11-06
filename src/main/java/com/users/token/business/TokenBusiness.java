package com.users.token.business;
import com.users.common.annotation.Business;
import com.users.common.error.ErrorCode;
import com.users.common.exception.ApiException;
import com.users.user.db.User;
import com.users.token.controller.model.TokenResponse;
import com.users.token.converter.TokenConverter;
import com.users.token.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Business
public class TokenBusiness {
    private final TokenService tokenService;
    private final TokenConverter tokenConverter;

    /**
     * 1. user entity user Id 추출
     * 2. access, refresh token 발행
     * 3. converter -> token response로 변경
     */

    public TokenResponse issueToken(User userEntity){

        return Optional.ofNullable(userEntity)
                .map(user -> {
                    return user.getId();
                })
                .map(user -> {
                    Integer id = userEntity.getId();
                    String role = userEntity.getRole().name();
                    var accessToken = tokenService.issueAccessToken(id);
                    var refreshToken = tokenService.issueRefreshToken(id);
                    return tokenConverter.toResponse(accessToken, refreshToken, role);
                })
                .orElseThrow(
                        ()-> new ApiException(ErrorCode.NULL_POINT)
                );
    }

    public String validationAccessToken(String accessToken){
        var id = tokenService.validationToken(accessToken);
        return id;
    }

    public String getUserIdFromToken(String token) {
        // TokenService에서 이메일을 추출하는 메서드를 호출
        return tokenService.validationToken(token);
    }

    public String getUserRoleFromToken(String accessToken) {
        // 토큰에서 역할 정보를 추출하는 메서드를 호출하여 role 변수에 할당
        String role = tokenService.validationToken(accessToken);
        return role;
    }
}