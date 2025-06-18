package com.interviewQuestion.testTask.dto;

import com.interviewQuestion.testTask.model.TaskStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskRequestDTO {

    @NotBlank
    @Size(max = 100, message = "Should be at most 100")
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status=TaskStatus.PENDING;
}
