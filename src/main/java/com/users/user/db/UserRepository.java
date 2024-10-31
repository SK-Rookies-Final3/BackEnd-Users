package com.users.user.db;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findPasswordByUsername(String password);
    Optional<User> findByNickname(String Nickname);
    Optional<User> findByEmail(String email);
    Optional<User> findInfoByUsername(String username);
    Optional<User> findUsernameByNickname(String nickname);
    Optional<User> findNicknameByUsername(String username);
    Optional<User> findById(Integer id);

}