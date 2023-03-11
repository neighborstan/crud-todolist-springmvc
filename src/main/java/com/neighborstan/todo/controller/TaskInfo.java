package com.neighborstan.todo.controller;

import com.neighborstan.todo.domain.Status;
import lombok.Data;

@Data
public class TaskInfo {
    private String description;
    private Status status;
}
