package com.niit.ToDoService.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Transient //this particular filed i don't want to persist to mongodb
    public static final String SEQUENCE_NAME= "user_sequence";//our taskid is our sequence name
    @Id
    private int taskId;
    private String taskTitle;
    private String taskStatus;
    private String taskDescription;
    @Enumerated(EnumType.STRING)
    private BasedOnPriority taskPriority;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm")// for accepting date from jsonFormat, without this annotation we should send in yyyy/MM/dd format
    private Date taskDeadline;


}
