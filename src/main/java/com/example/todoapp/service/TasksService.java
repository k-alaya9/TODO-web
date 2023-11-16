package com.example.todoapp.service;


import com.example.todoapp.entitiy.Tasks;
import com.example.todoapp.repo.TasksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasksService {


    @Autowired
    private TasksRepo tasksRepo;


    public List<Tasks> findBYUserid(Long UserId){
        return tasksRepo.findByUserId(UserId);
    }

    public Tasks createTask(Tasks tasks){
        return tasksRepo.saveAndFlush(tasks);
    }

    public Tasks editOne(Long id){
       Tasks tasks= tasksRepo.findById(id).orElse(null);
       if(tasks!=null){
           tasks.setFinished(true);
           return tasksRepo.save(tasks);
       }
       throw new RuntimeException("not found");
    }

    public void delete(Long id){
        Tasks t= tasksRepo.findById(id).orElse(null);
        if(t!=null){
            tasksRepo.delete(t);
        }
        else{
            throw new RuntimeException("Not Found");
        }
    }
}
