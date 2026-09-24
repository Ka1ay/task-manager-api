package com.bulat.taskmanager.dto;

import com.bulat.taskmanager.entity.TaskPriority;
import com.bulat.taskmanager.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateTaskRequest {

    @NotBlank(message = "Title must not be blank")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Priority is required")
    private TaskPriority priority;

    @NotNull(message = "Status is required")
    private TaskStatus status;
}
