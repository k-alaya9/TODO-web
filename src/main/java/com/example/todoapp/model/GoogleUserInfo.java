package com.example.todoapp.model;

import java.util.Map;

public class GoogleUserInfo {

    private Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        System.out.println(attributes);
    }

    public String getId() {
        return (String) attributes.get("sub");
    }

    public String getName() {
        return (String) attributes.get("name");
    }
    public String getPicture(){
        return (String) attributes.get("picture");
    }
    public String getEmail() {
        return (String) attributes.get("email");
    }

}