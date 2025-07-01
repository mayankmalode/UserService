package com.example.userservice.security.services;

import com.example.userservice.models.User;
import com.example.userservice.repositiories.UserRepository;
import com.example.userservice.security.models.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);

        if(optionalUser.isEmpty())
        {
            throw new UsernameNotFoundException("Username: " + username + " not found");
        }

        User user = optionalUser.get();
        //Convert user to UserDetails type of object.

        return new CustomUserDetails(user);
    }
}
