package com.nathanaelburt.taskmanager.controllers;

import com.nathanaelburt.taskmanager.entity.Task;
import com.nathanaelburt.taskmanager.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@CrossOrigin
@Controller
public class TaskController {

    @RequestMapping(value = "/tasks", produces = "application/json")
    public ResponseEntity index() {
        TaskService taskService = new TaskService();
        List<Task> tasks = taskService.findAll();
        return ResponseEntity.ok(tasks);
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity showTask(@PathVariable(value = "id") Long id) {
        TaskService taskService = new TaskService();
        Task task = taskService.findById(id);
        return ResponseEntity.ok(task);
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity createTask(@RequestBody Task task) {
        TaskService taskService = new TaskService();
        taskService.save(task);
        return ResponseEntity.ok(task);
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateTask(@PathVariable(value = "id") Long id, @RequestBody Task task) {
        TaskService taskService = new TaskService();
        task.setId(id);
        taskService.update(task);
        return ResponseEntity.ok(task);
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity deleteTask(@PathVariable(value = "id") Long id) {
        TaskService taskService = new TaskService();
        taskService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
