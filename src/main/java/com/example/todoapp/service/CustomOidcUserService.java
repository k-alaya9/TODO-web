package com.example.todoapp.service;

import com.example.todoapp.entitiy.User;
import com.example.todoapp.model.GoogleUserInfo;
import com.example.todoapp.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private UserRepo userRepository;


    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        try {
            return processOidcUser(oidcUser);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OidcUser processOidcUser(OidcUser oidcUser) throws IOException {
        GoogleUserInfo googleUserInfo = new GoogleUserInfo(oidcUser.getAttributes());

        Optional<User> userOptional = userRepository.findByUsername(googleUserInfo.getName());
        if (!userOptional.isPresent()) {
            User user = new User();
            user.setId(Long.getLong(googleUserInfo.getId()));
            user.setUsername(googleUserInfo.getName());
            downloadImage(googleUserInfo.getPicture(),"C:/Users/DELL/Documents/spring boot/todoapp/src/main/resources/profilePicture/"+googleUserInfo.getName()+".jpg");
            user.setPicPath("/profilePicture/"+googleUserInfo.getName()+".jpg");
           User u= userRepository.save(user);
        }

        return oidcUser;
    }
    public static void downloadImage(String imageUrl, String destinationPath) throws  IOException {
        URL url = new URL(imageUrl);
        BufferedInputStream in = new BufferedInputStream(url.openStream());
        FileOutputStream out = new FileOutputStream(destinationPath);

        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
            out.write(buffer, 0, bytesRead);
        }

        out.close();
        in.close();
    }
}
