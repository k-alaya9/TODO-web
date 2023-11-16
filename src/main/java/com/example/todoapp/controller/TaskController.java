package com.example.todoapp.controller;


import com.example.todoapp.entitiy.Tasks;
import com.example.todoapp.entitiy.User;
import com.example.todoapp.requests.TaskRequest;
import com.example.todoapp.service.TasksService;
import com.example.todoapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    private TasksService tasksService;
    @Autowired
    private UserService userService;

    @PostMapping("/tasks")
    public Tasks addTask(@RequestBody TaskRequest taskRequest){
        Long userId=profile().getId();
        Tasks task= new Tasks(
                taskRequest.getTitle(),
                taskRequest.getStartTime(),
                taskRequest.getEndTime(),
                taskRequest.isFinished(),
                userId
        );
        return tasksService.createTask(task);
    }
    @GetMapping("/tasks")
    public List<Tasks> getTasks(){
        if(profile()!=null)
        return tasksService.findBYUserid(profile().getId());
        throw  new RuntimeException("Not found");
    }

    @PutMapping("/task/{id}")
    public Tasks editTask(@PathVariable Long id){
        return tasksService.editOne(id);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        tasksService.delete(id);
        Map<String,String>body=new HashMap<>();
        body.put("Message","Deleted");
        return ResponseEntity.ok(body);
    }
    @GetMapping("/user")
    public User profile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User u= userService.findByUsername(authentication.getName());
        if(u==null){
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OAuth2User user = oauthToken.getPrincipal();
            String name = user.getAttribute("name");
            u=userService.findByUsername(name);
            return u;
        }else{
            return u;
        }
    }
}
