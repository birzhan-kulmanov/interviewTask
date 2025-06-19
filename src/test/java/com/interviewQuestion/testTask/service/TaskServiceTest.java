package com.interviewQuestion.testTask.service;

import com.interviewQuestion.testTask.dto.TaskRequestDTO;
import com.interviewQuestion.testTask.dto.TaskResponseDTO;
import com.interviewQuestion.testTask.model.Task;
import com.interviewQuestion.testTask.model.TaskStatus;
import com.interviewQuestion.testTask.repository.TaskRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask_shouldReturnSavedTask() {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO("New task", "Task Description", null);

        Task task = new Task();
        UUID taskId = UUID.randomUUID();
        task.setId(taskId);
        task.setTitle("New task");
        task.setDescription("Task Description");
        task.setStatus(TaskStatus.PENDING);

        TaskResponseDTO responseDTO = new TaskResponseDTO(taskId, "New task", "Task Description", TaskStatus.PENDING, null, null);

        when(modelMapper.map(taskRequestDTO, Task.class)).thenReturn(task);
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(modelMapper.map(task, TaskResponseDTO.class)).thenReturn(responseDTO);

        TaskResponseDTO result = taskService.createTask(taskRequestDTO);

        assertEquals(taskId, result.getId());
        assertEquals("New task", result.getTitle());
        assertEquals("Task Description", result.getDescription());
        assertEquals(TaskStatus.PENDING, result.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

}