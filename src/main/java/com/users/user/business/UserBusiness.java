package com.users.user.business;

import com.users.common.annotation.Business;
import com.users.token.business.TokenBusiness;
import com.users.token.controller.model.TokenResponse;
import com.users.user.User;
import com.users.user.dto.UserLoginRequest;
import com.users.user.dto.UserMapper;
import com.users.user.dto.UserRegisterRequest;
import com.users.user.dto.UserResponse;
import com.users.user.dto.UserUpdateRequest;
import com.users.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Business
public class UserBusiness {
    // UserService로 이동 전 로직 처리
    private final UserService userService;
    private final UserMapper userMapper;
    private final TokenBusiness tokenBusiness;

    /*
     * 1. 회원가입(request) -> entity
     * 2. entity -> 데이터베이스에 저장
     * 3. 저장된 엔티티 -> response
     * 4. response 반환
     */
    public UserResponse register(UserRegisterRequest request) {
        var entity = userMapper.toEntity(request);
        var newEntity = userService.register(entity);
        var response = userMapper.toResponse(newEntity);
        return response;
    }

    /**
     * 1. userId, password 를 가지고 사용자 체크
     * 2. user entity 로그인 확인
     * 3. token 생성(토큰 비즈니스의 이슈토큰)
     * 4. token response
     */
    public TokenResponse login(UserLoginRequest request) {
        var userEntity = userService.login(request);
        var tokenResponse = tokenBusiness.issueToken(userEntity);
        return tokenResponse;
    }


    /**
     * 회원정보 조회:
     * 1. 사용자 ID로 회원 정보를 조회
     * 2. 조회된 엔티티를 response로 변환하여 반환
     */
    public UserResponse getUserById(Integer id) {
        var userEntity = userService.getUserById(id);
        return userMapper.toResponse((User) userEntity);  // Entity -> Response 변환
    }


}