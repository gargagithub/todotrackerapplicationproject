package com.niit.ToDoService.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveDTO {
    private Task task;
    private String userEmail;
    private String TaskType;
}
