package com.javaProjects.taskMaster.repository;

import com.javaProjects.taskMaster.model.Tasks;
import com.javaProjects.taskMaster.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Tasks,Long> {
    List<Tasks> findByUser(Optional<Users> user);

    Optional<Tasks> findByTopicAndUser(String titleName, Users user);
}
