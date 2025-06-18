package com.interviewQuestion.testTask.service;

import com.interviewQuestion.testTask.dto.TaskRequestDTO;
import com.interviewQuestion.testTask.dto.TaskResponseDTO;
import com.interviewQuestion.testTask.exceptions.ResourceNotFoundException;
import com.interviewQuestion.testTask.model.Task;
import com.interviewQuestion.testTask.model.TaskStatus;
import com.interviewQuestion.testTask.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ModelMapper modelMapper;

    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        Task task = modelMapper.map(taskRequestDTO, Task.class);
        task.setStatus(TaskStatus.PENDING);
        task = taskRepository.save(task);
        return modelMapper.map(task, TaskResponseDTO.class);
    }

    public TaskResponseDTO getTaskById(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Task", "taskId", id));
        return modelMapper.map(task, TaskResponseDTO.class);
    }

    public TaskResponseDTO updateTask(UUID id, TaskRequestDTO taskRequestDTO) {
        Task savedTask = taskRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Task", "taskId", id));
        savedTask.setTitle(taskRequestDTO.getTitle());
        savedTask.setDescription(taskRequestDTO.getDescription());

        if(taskRequestDTO.getStatus() != null) {
            savedTask.setStatus(taskRequestDTO.getStatus());
        }
        savedTask = taskRepository.save(savedTask);
        return modelMapper.map(savedTask, TaskResponseDTO.class);
    }

    public void deleteTask(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Task", "taskId", id));
        taskRepository.delete(task);
    }
}
