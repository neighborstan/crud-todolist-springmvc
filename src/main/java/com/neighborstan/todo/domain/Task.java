package com.neighborstan.todo.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(schema = "todo", name = "task")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    @Enumerated(EnumType.ORDINAL)
    private Status status;
}
