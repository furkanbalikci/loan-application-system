package com.example.loanapp.service;

import com.example.loanapp.model.User;
import com.example.loanapp.repository.UserRepository;
import com.mongodb.BSONTimestampCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //if user not exist then save user to db.
    public User addUser(User newUser) {
        Optional<User> user = userRepository.findByUsername(newUser.getUsername());
        if (user.isEmpty()) {
            return userRepository.save(newUser);
        }
        newUser.setDbStatus(true);
        return newUser;
    }

}
