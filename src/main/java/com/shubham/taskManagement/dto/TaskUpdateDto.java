package com.shubham.taskManagement.dto;

import com.shubham.taskManagement.CustomAnnotations.DateAnnotations.ValidFutureDate;
import com.shubham.taskManagement.CustomAnnotations.DateAnnotations.ValidPastDate;
import com.shubham.taskManagement.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class TaskUpdateDto {
    @NotBlank
    private UUID id;

    private String title;

    private String description;

    private List<String> labels;

    private Date endDate;

    private Long assigneeId;

    private List<UUID> dependencies;

    private List<UUID> dependencyOf;

    private Long verifierId;

    private TaskStatus taskStatus;
}
