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

        // Model Mapper используется чтобы преобразовать обьект одного класса в другой
        Task task = modelMapper.map(taskRequestDTO, Task.class);

        // Когда создаем задачу его статус по дефолту PENDING
        task.setStatus(TaskStatus.PENDING);
        task = taskRepository.save(task);
        return modelMapper.map(task, TaskResponseDTO.class);
    }

    public TaskResponseDTO getTaskById(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Task", "taskId", id)); // Выкидываем exception если не нашли задачу по айдишке
        return modelMapper.map(task, TaskResponseDTO.class);
    }

    public TaskResponseDTO updateTask(UUID id, TaskRequestDTO taskRequestDTO) {
        Task savedTask = taskRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Task", "taskId", id)); // Выкидываем exception если не нашли задачу по айдишке

        // Перезаписываем данные задачи из БД на новые которые ввел Пользователем
        savedTask.setTitle(taskRequestDTO.getTitle());
        savedTask.setDescription(taskRequestDTO.getDescription());

        // Меняем статус только если оно введено Пользователем. Иначе остается так как было
        if(taskRequestDTO.getStatus() != null) {
            savedTask.setStatus(taskRequestDTO.getStatus());
        }
        savedTask = taskRepository.save(savedTask);
        return modelMapper.map(savedTask, TaskResponseDTO.class);
    }

    public void deleteTask(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Task", "taskId", id)); // Выкидываем exception если не нашли задачу по айдишке
        taskRepository.delete(task);
    }
}
