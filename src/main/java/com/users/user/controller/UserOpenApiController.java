package com.users.user.controller;
import com.users.common.api.Api;
import com.users.token.controller.model.TokenResponse;
import com.users.user.business.UserBusiness;
import com.users.user.dto.UserLoginRequest;
import com.users.user.dto.UserRegisterRequest;
import com.users.user.dto.UserResponse;
import com.users.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/user")
public class UserOpenApiController {

    private final UserService userService;
    private final UserBusiness userBusiness;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<Api<UserResponse>> register(
            @Valid @RequestBody UserRegisterRequest request
    ) {
        // username 중복 체크 (DB에서 직접 확인)
        if (userService.isUsernameTaken(request.getUsername())) {
            return ResponseEntity.badRequest().build();
        }

        // 역할 값 출력 (디버깅)
        System.out.println("Role: " + request.getRole());

        var response = userBusiness.register(request);  // response가 UserResponse 타입이어야 함

        // role이 "owner"인 경우 추가 메시지 포함
        if ("owner".equalsIgnoreCase(String.valueOf(request.getRole()))) {
            return ResponseEntity.ok(Api.OKWithExtraMessage(response, "X"));
        } else {
            return ResponseEntity.ok(Api.OK(response));
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Api<TokenResponse>> login(
            @Valid @RequestBody UserLoginRequest request
    ){
        var response = userBusiness.login(request);
        return ResponseEntity.ok(Api.OK(response));
    }
}