package com.bulat.taskmanager.repository;

import com.bulat.taskmanager.entity.Task;
import com.bulat.taskmanager.entity.TaskPriority;
import com.bulat.taskmanager.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByStatusAndPriority(TaskStatus status, TaskPriority priority, Pageable pageable);

    Page<Task> findByStatus(TaskStatus status, Pageable pageable);

    Page<Task> findByPriority(TaskPriority priority, Pageable pageable);
}
