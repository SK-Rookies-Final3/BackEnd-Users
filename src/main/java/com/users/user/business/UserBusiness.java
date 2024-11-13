package com.users.user.business;
import com.users.common.annotation.Business;
import com.users.token.business.TokenBusiness;
import com.users.token.controller.model.TokenResponse;
import com.users.user.db.User;
import com.users.user.db.UserRepository;
import com.users.user.dto.UserLoginRequest;
import com.users.user.dto.UserMapper;
import com.users.user.dto.UserRegisterRequest;
import com.users.user.dto.UserResponse;
import com.users.user.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Business
public class UserBusiness {
    // UserService로 이동 전 로직 처리
    private final UserService userService;
    private final UserMapper userMapper;
    private final TokenBusiness tokenBusiness;
    private Object userEntity;
    private final UserRepository userRepository; // User 엔터티를 관리하는 리포지토리

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



//master 관련 역할 부여

    // ID로 사용자 조회 및 역할 체크
    public UserResponse getUserByIdWithRoleCheck(String role) {
        // 역할이 "MASTER"인지 확인
        if (!"MASTER".equals(role.strip())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다."); // 권한 없음 예외
        }

        // 역할이 "MASTER"인 경우 UserResponse로 변환하여 반환
        return userMapper.toResponse((User) userEntity); // Entity -> Response 변환
    }

    public List<UserResponse> getAllUsers() {
        // 데이터베이스에서 모든 사용자 조회
        List<User> users = userRepository.findAll();

        // User 엔터티 리스트를 UserResponse 리스트로 변환
        return users.stream()
                .map(userMapper::toResponse)
                .toList();
    }
}