package com.javaProjects.taskMaster.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

//Generates Getters and Setters
@Data
//Generates Argument Constructor
@AllArgsConstructor
//Generate No-args Constructor
@NoArgsConstructor
//Enable Builder design pattern
@Builder

//Tells that the class is an Entity class or a Model Class
@Entity
public class Users {

    @Id
    @SequenceGenerator(
            name = "User_Sequence",
            sequenceName = "User_Sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "User_Sequence"
    )
    private int userId;

//    The userName can not be changed by the user therefore set False in updatable attribute
    @Column(nullable = false,unique = true, updatable = false)
    private String userName;

    @Column(nullable = false, updatable = true)
    private String userPassword;

    @Embedded
    private UserData userData;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<Tasks> tasks;

}
