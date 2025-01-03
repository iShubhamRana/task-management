package com.shubham.taskManagement.dao;

import com.shubham.taskManagement.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepo extends JpaRepository<Task, UUID> {
    public List<Task> findAllByAssigneeId(Long id);
    public List<Task> findAllByCreatedById(Long id);
}
