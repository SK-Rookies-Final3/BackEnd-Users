package com.users.user.service;
import com.users.common.error.ErrorCode;
import com.users.common.exception.ApiException;
import com.users.token.business.TokenBusiness;
import com.users.user.db.User;
import com.users.user.db.UserRepository;
import com.users.user.dto.UserLoginRequest;
import com.users.user.dto.UserMapper;
import com.users.user.dto.UserResponse;
import com.users.user.dto.UserUpdateRequest;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final TokenBusiness tokenBusiness;

    // 중복된 username 확인
    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public User register(User user) {
        // 중복 username 확인
        if (isUsernameTaken(user.getUsername())) {
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        //nickname을 username으로 설정
        user.setNickname(user.getNickname() != null ? user.getNickname() : user.getUsername());

        user.setCreatedAt(LocalDateTime.now());  // createdAt 설정
        return userRepository.save(user);
    }

    public User login(UserLoginRequest loginRequest) {
        User user = userRepository.findPasswordByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST));

        boolean passwordMatch = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if (!passwordMatch) {
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }
        return user;
    }

    /**
     * 회원정보 등록:
     * 1. 사용자 ID로 기존 회원 정보를 조회하여 가져옴
     * 2. 등록 요청에 포함된 정보에 따라 회원 정보를 업데이트
     * - 필드별로 입력 값이 있을 때만 해당 필드를 업데이트하도록 설정
     * 3. 수정된 회원 정보를 데이터베이스에 저장하고 UserResponse 객체로 변환하여 반환
     */
    public UserResponse updateUserInfo(int id, UserUpdateRequest request) {
        // ID로 기존 사용자 조회 (ID는 메서드 매개변수로 받아야 함)
        User user = userRepository.findById(id) // id를 매개변수로 받거나, 메서드 안에서 설정해야 합니다.
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // 요청에 있는 정보로 사용자 업데이트
        if (request.getNickname() != null) {
            user.setNickname(request.getNickname());
        }
        if (request.getAddress() != null) {
            user.setAddress(request.getAddress());
        }
        if (request.getAge() != null) {
            user.setAge(request.getAge());
        }
        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }
        if (request.getHeight() != null) {
            user.setHeight(request.getHeight());
        }
        if (request.getWeight() != null) {
            user.setWeight(request.getWeight());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        // 업데이트된 사용자 저장
        User updatedUser = userRepository.save(user);

        // User 객체를 UserResponse 객체로 변환하여 반환
        return userMapper.toResponse(updatedUser);
    }

    /**
     * 회원 정보 조회:
     * 1. 사용자 ID로 회원 정보를 조회
     * 2. 조회된 회원 엔티티 반환
     */
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }

    /**
     * 회원 탈퇴:
     * 1. 사용자 ID로 회원 정보를 조회
     * 2. 해당 사용자 삭제
     */
    public void deleteUserById(int id) {
        // ID를 기반으로 사용자를 조회하고, 없으면 예외 발생
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        // 사용자가 존재하면 삭제
        userRepository.delete(user);
    }
}