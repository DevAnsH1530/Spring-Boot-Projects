package com.javaProjects.taskMaster.repository;

import com.javaProjects.taskMaster.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {


    boolean existsByUserName(String user_name);
    List<Users> findByUserNameContaining( String userName);
    Users findByUserName(String userName);
    void deleteByUserName(String userName);

}
