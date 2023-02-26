package com.example.loanapp.service;

import com.example.loanapp.model.User;
import com.example.loanapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
//Service implementation required to retrieve user data from database
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        BCryptPasswordEncoder bCryptPasswordEncoder =  new BCryptPasswordEncoder();
        String pass = bCryptPasswordEncoder.encode(user.get().getPassword());

        if (user.isEmpty()){
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.get().getUsername())
                .password(pass)
                .authorities(user.get().getUserRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toList()))
                .build();
    }

}
