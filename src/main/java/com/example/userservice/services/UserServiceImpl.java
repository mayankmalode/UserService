package com.example.userservice.services;

import com.example.userservice.exceptions.ValidTokenNotFoundException;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.repositiories.TokenRepository;
import com.example.userservice.repositiories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private TokenRepository tokenRepository;

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token login(String email, String password) {
        /*
            1. Find the user by email.
            2. If user is not found, return null.
            3. If user is present in DB, then verify the password.
            4. If password matches, then generate the token and return it.
        */

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty())
        {
            return null;
        }

        User user = optionalUser.get();

        Token token = null;
        //Match the password.
        if(passwordEncoder.matches(password, user.getPassword()))
        {
            //Login is successful.
            token = new Token();

            token.setUser(user);

            //Expiry time should be 30 days from now.
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 30);
            Date date30daysFromNow = calendar.getTime();

            token.setExpiryAt(date30daysFromNow);

            //token value can be random String of 128 characters.
            token.setValue(RandomStringUtils.randomAlphanumeric(128));
        }
        return tokenRepository.save(token);
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
        user.setPassword(passwordEncoder.encode(password));
        user.setVerified(true);

        return userRepository.save(user);
    }

    @Override
    public void logout(String tokenValue) throws ValidTokenNotFoundException {
        //We will be able to logout a particular token
        //if token is present in DB, it's expiry time is greater than current time.
        //and isDeleted is false

        Optional<Token> optionalToken = tokenRepository.
                findByValueAAndDeletedAndExpiryAtGreaterThan(tokenValue, false, new Date());


        if(optionalToken.isEmpty()) {
            throw new ValidTokenNotFoundException("Valid token not found.");
        }

        Token token = optionalToken.get();
        token.setDeleted(true);
        tokenRepository.save(token);
    }

    @Override
    public User validateToken(String tokenValue) throws ValidTokenNotFoundException {

        Optional<Token> optionalToken = tokenRepository.
                findByValueAAndDeletedAndExpiryAtGreaterThan(tokenValue, false, new Date());

        if(optionalToken.isEmpty()) {
            throw new ValidTokenNotFoundException("Valid token not found.");
        }

        Token token = optionalToken.get();
        return token.getUser();
    }
}
