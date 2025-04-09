package com.fund.fund_transfer.repository;

import com.fund.fund_transfer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, String> {
    Optional<User> findByUserUuid(String userUuid);

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
