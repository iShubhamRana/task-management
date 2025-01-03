package com.shubham.taskManagement.service;

import com.shubham.taskManagement.dao.TaskRepo;
import com.shubham.taskManagement.dto.TaskUpdateDto;
import com.shubham.taskManagement.enums.TaskStatus;
import com.shubham.taskManagement.models.Task;
import com.shubham.taskManagement.models.UserModel;
import com.shubham.taskManagement.models.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private UserService userService;

    public void populateAdditionTaskInfo(Task task) {
        task.setCreatedDate(new Date());
        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        Authentication authObj = SecurityContextHolder.getContext().getAuthentication();

        UserPrincipal userPrincipal = (UserPrincipal) authObj.getPrincipal();

        UserModel user = userPrincipal.getUser();
        task.setCreatedById(user.getId());
    }

    public void createTask(Task task) throws Exception {
        if (!userService.isValidUser(task.getAssigneeId())) {
            throw new Exception("Invalid assignee id");
        }
        if (!userService.isValidUser(task.getVerifierId())) {
            throw new Exception("Invalid verifier id");
        }
        this.populateAdditionTaskInfo(task);
        taskRepo.save(task);
    }

    public Task getTaskWithId(UUID id) throws Exception {
        Optional<Task> task = taskRepo.findById(id);
        if (task.isPresent()) {
            return task.get();
        } else {
            throw new Exception("Task with given id not found");
        }
    }

    public List<Task> getTasksForAssignee(Long assigneeId) {
        return taskRepo.findAllByAssigneeId(assigneeId);
    }

    public List<Task> getTasksForCreator(Long createdById) {
        return taskRepo.findAllByCreatedById(createdById);
    }

    public void updateTask(TaskUpdateDto updatedTask) throws Exception {
        System.out.println("Updating task");

        Task existingTask = getTaskWithId(updatedTask.getId());

        if (existingTask == null) {
            throw new Exception("Task with given id not found");
        }

        if (updatedTask.getTitle() != null) {
            existingTask.setTitle(updatedTask.getTitle());
        }

        if (updatedTask.getDescription() != null) {
            existingTask.setDescription(updatedTask.getDescription());
        }

        if (updatedTask.getLabels() != null) {
            existingTask.setLabels(updatedTask.getLabels());
        }

        if (updatedTask.getEndDate() != null) {
            if (updatedTask.getEndDate().before(existingTask.getCreatedDate())) {
                existingTask.setEndDate(updatedTask.getEndDate());
            }
        }

        System.out.println("Saving task");
        taskRepo.save(existingTask);

    }

}
