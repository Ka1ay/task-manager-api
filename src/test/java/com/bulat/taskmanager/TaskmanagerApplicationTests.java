package com.bulat.taskmanager;

import com.bulat.taskmanager.dto.CreateTaskRequest;
import com.bulat.taskmanager.dto.TaskResponse;
import com.bulat.taskmanager.dto.UpdateTaskRequest;
import com.bulat.taskmanager.entity.TaskPriority;
import com.bulat.taskmanager.entity.TaskStatus;
import com.bulat.taskmanager.exception.TaskNotFoundException;
import com.bulat.taskmanager.repository.TaskRepository;
import com.bulat.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class TaskmanagerApplicationTests {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void cleanDatabase() {
        taskRepository.deleteAll();
    }

    @Test
    void createsUpdatesAndDeletesTask() {
        TaskResponse created = taskService.createTask(request("Initial title", TaskPriority.HIGH));

        assertThat(created.getId()).isNotNull();
        assertThat(created.getStatus()).isEqualTo(TaskStatus.TODO);
        assertThat(created.getCreatedAt()).isNotNull();
        assertThat(created.getUpdatedAt()).isNotNull();

        TaskResponse updated = taskService.updateTask(created.getId(), updateRequest("Updated title", TaskPriority.LOW, TaskStatus.IN_PROGRESS));
        assertThat(updated.getTitle()).isEqualTo("Updated title");
        assertThat(updated.getPriority()).isEqualTo(TaskPriority.LOW);
        assertThat(updated.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);

        taskService.deleteTask(created.getId());
        assertThatThrownBy(() -> taskService.getTaskById(created.getId()))
                .isInstanceOf(TaskNotFoundException.class);
    }

    private UpdateTaskRequest updateRequest(String title, TaskPriority priority, TaskStatus status) {
        UpdateTaskRequest request = new UpdateTaskRequest();
        request.setTitle(title);
        request.setDescription("Description");
        request.setPriority(priority);
        request.setStatus(status);
        return request;
    }

    private CreateTaskRequest request(String title, TaskPriority priority) {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle(title);
        request.setDescription("Description");
        request.setPriority(priority);
        return request;
    }
}
