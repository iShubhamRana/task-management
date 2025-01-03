package com.shubham.taskManagement;

import com.shubham.taskManagement.dao.TaskRepo;
import com.shubham.taskManagement.dto.TaskUpdateDto;
import com.shubham.taskManagement.service.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import com.shubham.taskManagement.models.Task;
import com.shubham.taskManagement.enums.TaskStatus;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringJUnit4ClassRunner.class)
public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepo taskRepo;

    private Task task;

    private UUID taskId = UUID.randomUUID();

    CountDownLatch latch = new CountDownLatch(1);

    @Before
    public void setup() {
        task = new Task();
        task.setId(taskId);
        task.setVersion(0L);
        task.setTaskStatus(TaskStatus.COMPLETED);
        when(taskRepo.findById(task.getId())).thenReturn(Optional.of(task));
    }

    @Test
    public void testConcurrentUpdates() throws InterruptedException {
        // Simulate concurrent updates
        Thread thread1 = new Thread(() -> {
            try {
                TaskUpdateDto updateDto = new TaskUpdateDto();
                updateDto.setId(this.taskId);
                updateDto.setTaskStatus(TaskStatus.IN_PROGRESS);
                latch.await();
                taskService.updateTask(updateDto);
            } catch (Exception e) {
                // Handle exception
                System.out.println("Inside exception");
            }
        });
        System.out.println("shubham");
//
        Thread thread2 = new Thread(() -> {
            try {
                TaskUpdateDto updateDto = new TaskUpdateDto();
                updateDto.setId(this.taskId);
                updateDto.setTaskStatus(TaskStatus.COMPLETED);
                latch.await();
                taskService.updateTask(updateDto);
            } catch (Exception e) {

                System.out.println("Inside exception");
                // Handle exception
            }
        });
        thread1.start();
        thread2.start();

        latch.countDown();

        //wait for both the threads to complete
        thread1.join();
        thread2.join();

        // Verify that only one update was persisted
    }
}