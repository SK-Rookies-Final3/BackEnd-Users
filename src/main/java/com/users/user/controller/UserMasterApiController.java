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
@RequestMapping("/api/users/master")
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

        // 토큰에서 역할을 가져옴
        String role = tokenBusiness.getUserRoleFromToken(accessToken);

        // 역할 확인 및 사용자 조회
        try {
//            if (role == null || !"MASTER".equals(role.trim())) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 권한이 없는 경우 403 반환
//            }

            List<UserResponse> allUsers = userBusiness.getAllUsers();
            return ResponseEntity.ok(allUsers);
        } catch (ResponseStatusException e) {
            // 예외가 발생하면 해당 상태 코드로 응답
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }


//    //OWNER 권한 부여
//    @PatchMapping("owner/{id}")


    // 회원 강퇴 (MASTER 권한 필요)
//    @DeleteMapping("/exit/{targetId}")
//    public ResponseEntity<Void> deleteUserAsMaster(
//            @RequestHeader("Authorization") String accessToken,
//            @PathVariable("targetId") int targetId) {
//
//        // 토큰을 검증하고 사용자 ID와 역할을 추출
//        String requesterId = tokenBusiness.getUserIdFromToken(accessToken); // ID 가져오기
//        String userRole = tokenBusiness.getUserRoleFromToken(accessToken);  // 역할 가져오기
//
//        // MASTER 권한이 있는 경우에만 회원 강퇴 허용
//        if ("MASTER".equals(userRole.strip())) {
//            userService.deleteUserById(targetId); // 대상 사용자를 삭제
//            return ResponseEntity.noContent().build(); // HTTP 204 No Content 응답 반환
//        } else {
//            // MASTER가 아닌 경우 권한이 없음을 반환
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//    }
}
