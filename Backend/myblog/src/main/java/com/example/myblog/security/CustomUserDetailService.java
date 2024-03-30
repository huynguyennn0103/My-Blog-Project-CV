package com.example.myblog.security;

import com.example.myblog.entity.User;
import com.example.myblog.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private IUserRepository iUserRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = iUserRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("Invalid email or email is not existed");
        }
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthoritySet);
    }
}
