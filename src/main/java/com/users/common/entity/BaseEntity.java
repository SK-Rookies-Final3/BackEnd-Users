package com.users.common.entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@MappedSuperclass
@SuperBuilder
public class BaseEntity {
    //식별키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
