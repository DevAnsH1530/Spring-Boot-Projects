package com.javaProjects.taskMaster.services;


import com.javaProjects.taskMaster.model.UserData;
import com.javaProjects.taskMaster.model.Users;
import com.javaProjects.taskMaster.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

//    created method for handling POSTMAPPING request for users
    public ResponseEntity<String> addUser(Users user){
        String str = user.getUserName();
        user.setUserName(str.trim());


        if(userRepo.existsByUserName(user.getUserName()))
            return new ResponseEntity<>("User"+user.getUserName()+"already exists in the system", HttpStatus.CONFLICT);
//        Encrypting the User's password
        user.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
        userRepo.save(user);
        return new ResponseEntity<>(user+"created successfully",HttpStatus.CREATED);
    }


//    created for handling GET request for displaying all users in the database
    public ResponseEntity<List<Users>> getAllUsers(Authentication authentication){
        List<Users> users = userRepo.findAll();
        if (users.isEmpty()) {
            // Handle the case where no users are found
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    public ResponseEntity<List<Users>> getUser(String userName){
        List<Users> res = userRepo.findByUserNameContaining(userName.trim());
//        Checking for if USER IS NOT FOUND
        if(res.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteUser(String userName) {
        Users users = userRepo.findByUserName(userName.trim());
//        Checking if USER IS NOT FOUND with the entered USERNAME
        if(users == null){
            return new ResponseEntity<>(userName+" doesn't exists", HttpStatus.NOT_FOUND);
        }
        userRepo.deleteById(users.getUserId());
        return new ResponseEntity<>("User with "+userName+" deleted successfully!!", HttpStatus.OK);
    }

    public ResponseEntity<Users> getMyDetails(String userName) {
        return new ResponseEntity<>(userRepo.findByUserName(userName), HttpStatus.OK);
    }

    public ResponseEntity<String> deleteMe(String username) {
        Users user = userRepo.findByUserName(username);
        Integer id = user.getUserId();
        userRepo.deleteById(id);
        return new ResponseEntity<>(user.getUserName()+" delted Successfully",HttpStatus.OK);
    }

    public ResponseEntity<String> updatePersonalDetails(UserData userData, String userName) {

        Users user = userRepo.findByUserName(userName);
        user.setUserData(userData);
        userRepo.save(user);

        return new ResponseEntity<>(user.getUserData()+" with username"+ user.getUserName()+" have been updated successfully",HttpStatus.OK);

    }

//    public ResponseEntity<String> modifyUserData(String userName, Users userData){
//        Users user = userRepo.findByUserName(userName.trim());
//        String oldUserName = user.getUserName();
//        String updatedUserName = userData.getUserName().trim();
//        if(userName.trim().isEmpty()) {
//            return new ResponseEntity<>("Enter Valid UserName", HttpStatus.CONFLICT);
//        }
//        if(!oldUserName.equals(updatedUserName)){
//            return new ResponseEntity<>("You can not change the existing UserName", HttpStatus.CONFLICT);
//        }
//        int id = user.getUserId();
//        userRepo.
//
//    }

}