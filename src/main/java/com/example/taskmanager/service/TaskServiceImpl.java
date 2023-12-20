package com.example.taskmanager.service;

import com.example.taskmanager.exception.ResourceNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        return optionalTask.orElse(null);
    }

    @Override
    public Task createTask(@Valid Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long taskId, @Valid Task updatedTask) {
        if (taskRepository.existsById(taskId)) {
            updatedTask.setId(taskId);
            return taskRepository.save(updatedTask);
        } else {
            throw new ResourceNotFoundException("Task not found");
        }
    }

    @Override
    public void deleteTask(Long taskId) {
        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
        } else {
            throw new ResourceNotFoundException("Task not found");
        }
    }
}
