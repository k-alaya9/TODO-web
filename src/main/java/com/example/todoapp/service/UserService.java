package com.example.todoapp.service;

import com.example.todoapp.entitiy.User;
import com.example.todoapp.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user= userRepo.findByUsername(username).orElse(null);
        if(user==null){
            throw new UsernameNotFoundException("User not found");
        }
            return user;
    }

    public Optional<User> findById(Long id){return userRepo.findById(id);}

    public User createOne(User user){
        return userRepo.save(user);
    }
    public User findByUsername(String username){
        return userRepo.findByUsername(username).orElse(null);
    }
}
