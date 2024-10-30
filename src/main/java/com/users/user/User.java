package com.users.user;
import com.users.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@Component
@SuperBuilder
@EqualsAndHashCode(callSuper = false) // Lombok 경고 해결
public class User  {
    // id 타입을 BaseEntity와 일치시키기 위해 int로 수정
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column
    private String nickname;
    @Column
    private String address;
    @Column
    private int age;
    @Column
    private String gender;
    @Column
    private int height;
    @Column
    private int weight;
    @Column
    private String email;
    @Enumerated(EnumType.STRING)
    @Column
    private UserRole role;
    @Column
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column
    private LocalDateTime lastAccpetedAt;
}