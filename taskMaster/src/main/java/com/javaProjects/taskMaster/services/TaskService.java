package com.javaProjects.taskMaster.services;

import com.javaProjects.taskMaster.model.Tasks;
import com.javaProjects.taskMaster.model.Users;
import com.javaProjects.taskMaster.repository.TaskRepo;
import com.javaProjects.taskMaster.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TaskRepo taskRepo;

    // Get all tasks for a specific user
    public ResponseEntity<List<Tasks>> getAllTasks(String userName) {
        // Find user by username
        Optional<Users> userOptional = Optional.ofNullable(userRepo.findByUserName(userName));

        if (userOptional.isEmpty()) {
            // If user not found, return NOT_FOUND status
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        // Get tasks related to that user
        List<Tasks> allTasks = taskRepo.findByUser(Optional.of(userOptional.get()));

        if (allTasks.isEmpty()) {
            // Return an empty list instead of null
            return new ResponseEntity<>(List.of(), HttpStatus.OK);
        }

        return new ResponseEntity<>(allTasks, HttpStatus.OK);
    }

    // Add multiple tasks to the database
    // Add multiple tasks for a specific user
    public ResponseEntity<String> addTasks(String userName, List<Tasks> tasksList) {
        // Find the user by username
        Optional<Users> userOptional = Optional.ofNullable(userRepo.findByUserName(userName));

        // If user is not found, return NOT_FOUND response
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        // Retrieve the user entity
        Users user = userOptional.get();

        // If task list is null or empty, return BAD_REQUEST
        if (tasksList == null || tasksList.isEmpty()) {
            return new ResponseEntity<>("No tasks added because the task list is empty", HttpStatus.BAD_REQUEST);
        }

        // Associate each task with the user
        for (Tasks task : tasksList) {
            task.setUser(user);  // Set the user for each task
        }

        // Save all tasks to the database
        taskRepo.saveAll(tasksList);

        return new ResponseEntity<>(tasksList.size() + " tasks added to the database for user: " + userName, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteTaskByTitle(String titleName, String userName){
        // Check for TileName is Null or Blank
        if(titleName==null || titleName.trim().isBlank()){
            return new ResponseEntity<>("Enter valid titleName to search", HttpStatus.BAD_REQUEST);
        }

        Users user = userRepo.findByUserName(userName);

        // Find the task by title and user
        Optional<Tasks> taskOptional = taskRepo.findByTopicAndUser(titleName, user);

        // If task is not found, return NOT_FOUND response
        if (taskOptional.isEmpty()) {
            return new ResponseEntity<>("Task not found with the given title "+titleName, HttpStatus.NOT_FOUND);
        }
        System.out.println("Task to be deleted is "+taskOptional.get());
        List<Tasks> tasks = user.getTasks();
        tasks.remove(taskOptional.get());
        taskRepo.deleteById(taskOptional.get().getTaskId());
        return new ResponseEntity<>("Task with title '" + titleName + "' deleted successfully", HttpStatus.OK);

    }


    public ResponseEntity<String> updateTask(Tasks tasks, String username) {
        Users user = userRepo.findByUserName(username);
        for(Tasks task: user.getTasks()){
            if(task.getTopic().equals(tasks.getTopic())){
                return new ResponseEntity<>("Topic with "+tasks.getTopic()+" already exists in the system. Please keep another name",HttpStatus.FOUND);
            }
        }
        Optional<Tasks> taskToBeUpdated = taskRepo.findById(tasks.getTaskId());
        taskToBeUpdated.get().setTaskStatus(tasks.getTaskStatus());
        taskToBeUpdated.get().setTopic(tasks.getTopic());
        taskToBeUpdated.get().setDescription(tasks.getDescription());
        taskRepo.save(taskToBeUpdated.get());
        return new ResponseEntity<>("Topic with "+tasks.getTopic()+" updated successfully!!",HttpStatus.OK);
    }
}