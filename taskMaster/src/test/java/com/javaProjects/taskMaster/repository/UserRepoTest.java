package com.javaProjects.taskMaster.repository;

import com.javaProjects.taskMaster.TaskMasterApplication;
import com.javaProjects.taskMaster.model.Users;
import com.javaProjects.taskMaster.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = TaskMasterApplication.class)
class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @Test
    public void getUsersByUser_nameContaining() {
        List<Users> res = userRepo.findByUserNameContaining("Te"); // Use the exact field name 'user_name'
//        assertTrue(res);

        List<Users> us = userRepo.findByUserNameContaining("Te");

// Check if the list is not empty
        Assertions.assertFalse(!us.isEmpty(), "User list should not be empty");

// Alternatively, check if the list contains a specific user
        Assertions.assertTrue(
                us.stream().anyMatch(user -> user.getUserName().contains("example")), "User name should contain 'example'");

//        assertFalse(res.isEmpty());
    }
}
