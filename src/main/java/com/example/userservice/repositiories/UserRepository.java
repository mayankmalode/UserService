package com.example.userservice.repositiories;

import com.example.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);   //upsert

    @Override
    Optional<User> findById(Long aLong);

    Optional<User> findByEmail(String email);
}
