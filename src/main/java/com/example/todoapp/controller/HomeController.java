package com.example.todoapp.controller;


import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.InputStream;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "Hello,home";
    }

    @GetMapping("/secured")
    public String secured(){
        return "Hello,Secured";
    }

    @GetMapping("/profilePicture/{path}")
    public ResponseEntity photo(@PathVariable String path){
        MediaType contentType = path.contains("jpg") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
        InputStream in = getClass().getResourceAsStream("/profilePicture/"+path);
        return ResponseEntity.ok()
                .contentType(contentType)
                .body(new InputStreamResource(in));
    }
}
