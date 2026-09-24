package com.bulat.taskmanager.service;

import com.bulat.taskmanager.dto.CreateTaskRequest;
import com.bulat.taskmanager.dto.TaskResponse;
import com.bulat.taskmanager.dto.UpdateTaskRequest;
import com.bulat.taskmanager.entity.Task;
import com.bulat.taskmanager.entity.TaskPriority;
import com.bulat.taskmanager.entity.TaskStatus;
import com.bulat.taskmanager.exception.TaskNotFoundException;
import com.bulat.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public TaskResponse createTask(CreateTaskRequest request) {
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority())
                .status(TaskStatus.TODO)
                .build();

        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public Page<TaskResponse> getTasks(TaskStatus status, TaskPriority priority, Pageable pageable) {
        Page<Task> tasks;
        if (status != null && priority != null) {
            tasks = taskRepository.findByStatusAndPriority(status, priority, pageable);
        } else if (status != null) {
            tasks = taskRepository.findByStatus(status, pageable);
        } else if (priority != null) {
            tasks = taskRepository.findByPriority(priority, pageable);
        } else {
            tasks = taskRepository.findAll(pageable);
        }
        return tasks.map(this::mapToResponse);
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        return mapToResponse(findTask(id));
    }

    @Override
    @Transactional
    public TaskResponse updateTask(Long id, UpdateTaskRequest request) {
        Task task = findTask(id);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());

        return mapToResponse(taskRepository.saveAndFlush(task));
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        taskRepository.delete(findTask(id));
    }

    private Task findTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    private TaskResponse mapToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}
