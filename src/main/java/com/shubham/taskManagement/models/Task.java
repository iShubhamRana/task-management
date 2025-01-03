package com.shubham.taskManagement.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shubham.taskManagement.CustomAnnotations.DateAnnotations.ValidFutureDate;
import com.shubham.taskManagement.CustomAnnotations.DateAnnotations.ValidPastDate;
import com.shubham.taskManagement.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Version
    private Long version;

    @NotBlank(message = "Required field title")
    @Size(max = 200 , message = "Title should be more than 200 characters")
    private String title;

    @NotBlank(message = "Required field Description")
    private String description;

    @ElementCollection
    private List<String> labels;


    @ValidPastDate
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @ValidFutureDate
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date endDate;

    private Long createdById;

    @NotNull(message = "Required field assigneeId")
    private Long assigneeId;

    @ElementCollection
    private List<UUID> dependencies;

    @ElementCollection
    private List<UUID> dependencyOf;

    @NotNull(message = "Required field verifierId")
    private Long verifierId;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

}
