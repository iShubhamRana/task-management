package com.shubham.taskManagement.controllers.tasks;

import com.shubham.taskManagement.dto.ErrorResponse;
import com.shubham.taskManagement.dto.TaskUpdateDto;
import com.shubham.taskManagement.models.Task;
import com.shubham.taskManagement.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TasksPostController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/createTask")
    public ResponseEntity<?> createTask(@Valid @RequestBody Task task) {
        try {
            taskService.createTask(task);
            return ResponseEntity.ok("Task created successfully");
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /*
    *
    * Do we need transactional behaviour here ?
    * idts? since we are going to be doing only a single save
    *
    *
    * Let's use optimistic locking
    * */
    @PostMapping("/updateTask")
    public ResponseEntity<?> updateTask(@Valid @RequestBody TaskUpdateDto task) {

        //update the status to what is passed if
        try{
            taskService.updateTask(task);
            return ResponseEntity.ok("Task updated successfully");
        }catch ( Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }


    }


}
