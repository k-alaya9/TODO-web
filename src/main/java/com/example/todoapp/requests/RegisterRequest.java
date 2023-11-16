package com.example.todoapp.requests;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterRequest {

    private String username;
    private String password;
    private MultipartFile profilePicture;
}
