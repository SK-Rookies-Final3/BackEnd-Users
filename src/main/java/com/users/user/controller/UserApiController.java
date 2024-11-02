package com.users.user.controller;
import com.users.common.api.Api;
import com.users.token.business.TokenBusiness;
import com.users.user.business.UserBusiness;
import com.users.user.dto.UserResponse;
import com.users.user.dto.UserUpdateRequest;
import com.users.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class UserApiController {
    private final UserBusiness userBusiness;
    private final UserService userService;
    private final TokenBusiness tokenBusiness;

    // 회원 정보 등록
    @PatchMapping("/update")
    public ResponseEntity<UserResponse> updateUserInfo(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody UserUpdateRequest request) {

        //Business에 만들기
        var idString = tokenBusiness.validationAccessToken(accessToken);
        int id = Integer.valueOf(idString);

        UserResponse response = userService.updateUserInfo(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 회원 정보 조회
    @GetMapping()
    public Api<UserResponse> getUserInfo(@RequestHeader("Authorization") String accessToken) {
        //Business에 만들기
        var idString = tokenBusiness.validationAccessToken(accessToken);
        int id = Integer.valueOf(idString);

        UserResponse userResponse = userBusiness.getUserById(id);
        return Api.OK(userResponse);
    }

    // 회원 탈퇴
    @DeleteMapping("/exit/{id}")
    public Api<Void> deleteUser(@RequestHeader("Authorization") String accessToken) {

        //Business에 만들기
        var idString = tokenBusiness.validationAccessToken(accessToken);
        int id = Integer.valueOf(idString);

        // id 값을 기반으로 회원 탈퇴 진행
        userService.deleteUserById(id); // 반환값 없이 호출
        return Api.OK(null); // 또는 Api.NO_CONTENT(); 등의 방식으로 설정
    }
}