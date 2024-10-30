package com.users.token.helper;
import com.users.common.error.TokenErrorCode;
import com.users.common.exception.ApiException;
import com.users.token.Ifs.TokenHelperIfs;
import com.users.token.dto.TokenDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
@Component // 스프링 컴포넌트로 등록하여 IoC 컨테이너에서 관리되도록 설정
public class JwtTokenHelper implements TokenHelperIfs {
    @Value("${token.secret.key}") // 비밀 키를 환경 변수에서 가져옴
    private String secretKey;

    @Value("${token.access-token.plus-hour}") // 액세스 토큰 만료 시간 설정값을 환경 변수에서 가져옴
    private Long accessTokenPlusHour;

    @Value("${token.refresh-token.plus-hour}") // 리프레시 토큰 만료 시간 설정값을 환경 변수에서 가져옴
    private Long refreshTokenPlusHour;

    /**
     * 액세스 토큰을 발급하는 메서드
     *
     * @param data - 토큰에 포함될 클레임 데이터
     * @return - 발행된 액세스 토큰과 만료 정보를 포함하는 TokenDto 객체
     */
    @Override
    public TokenDto issueAccessToken(Map<String, Object> data) {
        // 만료 시간 설정: 현재 시간에서 설정된 시간만큼 더하여 LocalDateTime 객체 생성
        var expiredLocalDateTime = LocalDateTime.now().plusHours(accessTokenPlusHour);
        // 만료 시간을 Date 객체로 변환하여 JWT에서 사용할 수 있도록 설정
        var expiredAt = Date.from(expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
        // 비밀 키를 사용하여 HMAC SHA 키 생성
        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

        // JWT 액세스 토큰 생성
        var jwtToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256) // 서명 알고리즘 설정
                .setClaims(data) // 클레임 데이터 설정
                .setExpiration(expiredAt) // 만료 시간 설정
                .compact(); // 토큰 문자열 생성

        // 발행된 액세스 토큰과 만료 정보를 포함한 TokenDto 객체 반환
        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDateTime)
                .build();
    }

    /**
     * 리프레시 토큰을 발급하는 메서드
     *
     * @param data - 토큰에 포함될 클레임 데이터
     * @return - 발행된 리프레시 토큰과 만료 정보를 포함하는 TokenDto 객체
     */
    @Override
    public TokenDto issueRefreshToken(Map<String, Object> data) {
        // 만료 시간 설정: 현재 시간에서 설정된 시간만큼 더하여 LocalDateTime 객체 생성
        var expiredLocalDateTime = LocalDateTime.now().plusHours(refreshTokenPlusHour);
        // 만료 시간을 Date 객체로 변환하여 JWT에서 사용할 수 있도록 설정
        var expiredAt = Date.from(expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
        // 비밀 키를 사용하여 HMAC SHA 키 생성
        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

        // JWT 리프레시 토큰 생성
        var jwtToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256) // 서명 알고리즘 설정
                .setClaims(data) // 클레임 데이터 설정
                .setExpiration(expiredAt) // 만료 시간 설정
                .compact(); // 토큰 문자열 생성

        // 발행된 리프레시 토큰과 만료 정보를 포함한 TokenDto 객체 반환
        return TokenDto.builder()
                .token(jwtToken)
                .expiredAt(expiredLocalDateTime)
                .build();
    }

    /**
     * 토큰의 유효성을 검증하고 클레임 데이터를 반환하는 메서드
     *
     * @param token - 검증할 JWT 토큰
     * @return - 검증된 토큰의 클레임 데이터 맵
     * @throws ApiException - 검증 실패 시 예외를 발생시킴
     */
    @Override
    public Map<String, Object> validationTokenWithThrow(String token) {
        // 비밀 키를 사용하여 HMAC SHA 키 생성
        var key = Keys.hmacShaKeyFor(secretKey.getBytes());
        // JWT 파서 생성
        var parser = Jwts.parser().setSigningKey(key).build();

        try {
            // 토큰 검증 및 클레임 파싱
            var result = parser.parseClaimsJws(token);
            return new HashMap<>(result.getBody()); // 파싱된 클레임 데이터를 맵으로 반환

        } catch (SignatureException e) {
            // 서명 오류가 발생하면 INVALID_TOKEN 예외 발생
            throw new ApiException(TokenErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            // 만료된 토큰일 경우 EXPIRED_TOKEN 예외 발생
            throw new ApiException(TokenErrorCode.EXPIRED_TOKEN);
        } catch (Exception e) {
            // 기타 오류가 발생한 경우 TOKEN_EXCEPTION 예외 발생
            throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION);
        }
    }
}
