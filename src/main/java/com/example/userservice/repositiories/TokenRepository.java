package com.example.userservice.repositiories;

import com.example.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Override
    Token save(Token token);

    Optional<Token> findByValueAAndDeletedAndExpiryAtGreaterThan(String value, boolean deleted, Date expiryAt);
}
