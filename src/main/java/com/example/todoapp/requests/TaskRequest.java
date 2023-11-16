package com.example.todoapp.requests;


import lombok.*;

import java.util.Date;

@Data
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    private String title;
    private Date startTime;
    private Date endTime;
    private boolean finished;
}
