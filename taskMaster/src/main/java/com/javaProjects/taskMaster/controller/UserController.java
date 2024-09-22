package com.javaProjects.taskMaster.controller;

import com.javaProjects.taskMaster.model.UserData;
import com.javaProjects.taskMaster.model.Users;
import com.javaProjects.taskMaster.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/task-master")
public class UserController {

    @Autowired
    private UserService userService;

    // This endpoint is not secured
    @GetMapping("/home")
    public ResponseEntity<String> accessHome(){
        return new ResponseEntity<>("Welcome to Home Page", HttpStatus.OK);
    }

    // It will display data of all the Users
    @GetMapping("/users/displayAllUsers")
    public ResponseEntity<List<Users>> getAllUsers(Authentication authentication){
        if(authentication.isAuthenticated())
            return userService.getAllUsers(authentication);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // This endpoint will get the User by it's initials of the username
    @GetMapping("/users/getUserByUserName/{userName}")
    public ResponseEntity<List<Users>> getUsersByUser_nameContaining(@PathVariable String userName, Authentication authentication){
        if(authentication.isAuthenticated())
            return  userService.getUser(userName);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // This endpoint will get the details of currently logged-in user
    @GetMapping("/users/getMyDetails")
    public ResponseEntity<Users> getMyDetails(Authentication authentication){
        if(authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userService.getMyDetails(userDetails.getUsername());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // This endpoint will add the user and this endpoint is also not secured so that anyone can access it i.e. can create user.
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody Users user){
        return userService.addUser(user);
    }

    // This endpoint will delete the user by finding it's username
    @DeleteMapping("/users/deleteUserByUserName/{userName}")
    public ResponseEntity<String> deleteUser(@PathVariable String userName, Authentication authentication){
        if(authentication.isAuthenticated()) {
            return userService.deleteUser(userName);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // This endpoint will delete the current logged-in user
    @DeleteMapping("/users/deleteMe")
    public ResponseEntity<String> deleteMe(Authentication authentication){
        if(authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userService.deleteMe(userDetails.getUsername());
        }
        return new ResponseEntity<>("You don't have access to this  URL",HttpStatus.UNAUTHORIZED);
    }

    @PatchMapping("/users/updatePersonalDetails")
    public ResponseEntity<String> updatePersonalDetails(@RequestBody UserData userData, Authentication authentication){

        if(authentication.isAuthenticated()){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println("User: " + userDetails.getUsername());
            System.out.println("Authorities: " + userDetails.getAuthorities());
            return userService.updatePersonalDetails(userData, userDetails.getUsername());
        }
        return new ResponseEntity<>("Please Log-in with your UserName and Password",HttpStatus.UNAUTHORIZED);

    }
}
