package com.example.todoapp.entitiy;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String title;
    private Date startTime;
    private Date endTime;
    private boolean finished;

    @ManyToOne
    @JoinColumn(name = "fk_user",nullable = false)
    private User user;

    public Tasks(Long id){
        this.id=id;
    }
    public Tasks(String title, Date startTime, Date endTime,boolean finished, Long userId) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.finished=finished;
        this.user = new User(userId);
    }
    public Tasks(Long id,String title, Date startTime, Date endTime,boolean finished, Long userId) {
        this.id=id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.finished=finished;
        this.user = new User(userId);
    }
}
