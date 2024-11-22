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

    public TokenResponse issueToken(User userEntity) {

        return Optional.ofNullable(userEntity)
                .map(user -> {
                    Integer id = user.getId(); // user.getId()를 변수에 할당
                    String role = user.getRole().name();
                    var accessToken = tokenService.issueAccessToken(id);
                    var refreshToken = tokenService.issueRefreshToken(id);
                    return tokenConverter.toResponse(accessToken, refreshToken, role, id); // id를 그대로 사용
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }


    public String validationAccessToken(String accessToken){
        var id = tokenService.validationToken(accessToken);
        return id;
    }
}