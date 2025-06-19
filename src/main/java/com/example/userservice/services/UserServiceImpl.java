package com.example.userservice.services;

import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.repositiories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Token login(String email, String password) {
        return null;
    }

    @Override
    public User signUp(String name, String email, String password) {
        Optional<User> OptionalUser = userRepository.findByEmail(email);
        if(OptionalUser.isPresent()) {
            //Redirect user to the login page.
            return OptionalUser.get();
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setVerified(true);

        return userRepository.save(user);
    }

    @Override
    public void logout(String token) {

    }

    @Override
    public User validateToken(String token) {
        return null;
    }
}
