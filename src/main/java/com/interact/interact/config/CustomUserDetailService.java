package com.interact.interact.config;

import com.interact.interact.entity.User;
import com.interact.interact.exception.ResourceNotFoundException;
import com.interact.interact.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //loading user from database
       User user = this.userRepository.findByEmail(username).orElseThrow(()-> {return new ResourceNotFoundException("User","email => "+username, 2L);
        });
        return user;
    }
}
