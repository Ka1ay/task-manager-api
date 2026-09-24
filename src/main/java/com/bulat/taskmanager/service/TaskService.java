package com.bulat.taskmanager.service;

import com.bulat.taskmanager.dto.CreateTaskRequest;
import com.bulat.taskmanager.dto.TaskResponse;
import com.bulat.taskmanager.dto.UpdateTaskRequest;
import com.bulat.taskmanager.entity.TaskPriority;
import com.bulat.taskmanager.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    TaskResponse createTask(CreateTaskRequest request);

    Page<TaskResponse> getTasks(TaskStatus status, TaskPriority priority, Pageable pageable);

    TaskResponse getTaskById(Long id);

    TaskResponse updateTask(Long id, UpdateTaskRequest request);

    void deleteTask(Long id);
}
