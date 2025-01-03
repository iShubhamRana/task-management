package com.shubham.taskManagement.controllers.tasks;

import com.shubham.taskManagement.enums.TaskStatus;
import com.shubham.taskManagement.models.Task;
import com.shubham.taskManagement.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.shubham.taskManagement.dto.ErrorResponse;

import java.util.Date;
import java.util.UUID;

@RestController
public class TasksGetController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/getTask")
    public ResponseEntity<?> getTask(@RequestParam UUID taskId) {
        try {
            Task t = taskService.getTaskWithId(taskId);
            return ResponseEntity.ok(t);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/getTasksForAssignee")
    public ResponseEntity<?> getAllTasksForAssignee(@RequestParam Long assigneeId) {
        try {
            return new ResponseEntity<>(taskService.getTasksForAssignee(assigneeId), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body((new ErrorResponse(e.getMessage())));
        }
    }

    @GetMapping("/getTasksForCreator")
    public ResponseEntity<?> getAllTasksForCreator(@RequestParam Long createdById) {
        try {
            return new ResponseEntity<>(taskService.getTasksForCreator(createdById), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

}
