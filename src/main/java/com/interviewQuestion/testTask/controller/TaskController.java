package com.interviewQuestion.testTask.controller;

import com.interviewQuestion.testTask.dto.TaskRequestDTO;
import com.interviewQuestion.testTask.dto.TaskResponseDTO;
import com.interviewQuestion.testTask.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/tasks")
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO taskDTO = taskService.createTask(taskRequestDTO);
        return new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskResponseDTO> getTask(@PathVariable UUID id) {
        TaskResponseDTO taskDTO = taskService.getTaskById(id);
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable UUID id, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO taskDTO = taskService.updateTask(id, taskRequestDTO);
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
