package com.javaProjects.taskMaster.controller;

import com.javaProjects.taskMaster.model.Tasks;
import com.javaProjects.taskMaster.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/task-master")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/users/getAllTasks")
    public ResponseEntity<List<Tasks>> getAllTasks(Authentication authentication) {
        if(authentication.isAuthenticated()){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return taskService.getAllTasks(userDetails.getUsername());

        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/users/addTasks")
    public ResponseEntity<String> addTasks(@RequestBody List<Tasks> tasksList, Authentication authentication){
        if(authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return taskService.addTasks(userDetails.getUsername(), tasksList);
        }
        return new ResponseEntity<>("Please enter Username and Password",HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/users/deleteTask/{projectName}")
    public ResponseEntity<String> deleteTaskByTitle(@PathVariable String projectName, Authentication authentication){
        if(authentication.isAuthenticated()){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return taskService.deleteTaskByTitle(projectName, userDetails.getUsername());
        }
        return new ResponseEntity<>("Please enter Username and Password",HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/users/updateTask")
    public ResponseEntity<String> updateTask(@RequestBody Tasks tasks, Authentication authentication){
        if(authentication.isAuthenticated()){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return taskService.updateTask(tasks, userDetails.getUsername());
        }
        return new ResponseEntity<>("Please enter Username and Password",HttpStatus.UNAUTHORIZED);
    }

}
