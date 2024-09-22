package com.javaProjects.taskMaster.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserData {
    private String firstName;
    private String lastName;
    private int number;
    private String email;


}
