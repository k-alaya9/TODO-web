package com.example.todoapp.controller;

import com.example.todoapp.entitiy.User;
import com.example.todoapp.requests.RegisterRequest;
import com.example.todoapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@RestController
@RequestMapping("/auth/")
public class AuthController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;

    @GetMapping("register/")
    public String lol(){
        return "haha u are trash";
    }
    @PostMapping("register/")
    public User register(@ModelAttribute RegisterRequest request) throws IOException {


        var file=request.getProfilePicture();
        String filePath = Paths.get("C:/Users/DELL/Documents/spring boot/todoapp/src/main/resources/profilePicture/", file.getOriginalFilename()).toString();
        file.transferTo(new File(filePath));
        User user= new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                "/profilePicture/"+file.getOriginalFilename()
        );
        return userService.createOne(user);
    }
}
