package com.users.user.controller;
import com.users.token.business.TokenBusiness;
import com.users.user.business.UserBusiness;
import com.users.user.dto.UserResponse;
import com.users.user.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user/master")
public class UserMasterApiController {

    private final UserBusiness userBusiness;
    private final UserService userService;
    private final TokenBusiness tokenBusiness;

    @Autowired
    public UserMasterApiController(UserBusiness userBusiness, UserService userService, TokenBusiness tokenBusiness) {
        this.userBusiness = userBusiness;
        this.userService = userService;
        this.tokenBusiness = tokenBusiness;
    }

    // 회원 전체 조회 (MASTER 권한 필요)
    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAllUsers(
            @RequestHeader("Authorization") String accessToken) {
        // 역할 확인 및 사용자 조회
        try {
            List<UserResponse> allUsers = userBusiness.getAllUsers();
            return ResponseEntity.ok(allUsers);
        } catch (ResponseStatusException e) {
            // 예외가 발생하면 해당 상태 코드로 응답
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }

    // 회원 강퇴 (MASTER 권한 필요)
    @DeleteMapping("/exit/{targetId}")
    public ResponseEntity<Void> deleteUserAsMaster(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable("targetId") int targetId) {

        // 토큰을 검증하고 사용자 ID와 역할을 추출
        String requesterId = tokenBusiness.validationAccessToken(accessToken); // ID 가져오기

        userService.deleteUserByTargetId(targetId); // 대상 사용자를 삭제
        return ResponseEntity.noContent().build(); // HTTP 204 No Content 응답 반환
    }
}