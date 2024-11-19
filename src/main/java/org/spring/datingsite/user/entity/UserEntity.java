package org.spring.datingsite.user.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserEntity {
    private String id;
    private String session;
    private String firstName;
    private String middleName;
    private String lastName;
    private String sex;
    private String photo;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate birthDate;
    private Integer age;
    private String residence;
    private String aboutMe;

    public UserEntity(String id, String firstName, String lastName, String sex, String photo, String email, Integer age, String residence) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.photo = photo;
        this.email = email;
        this.age = age;
        this.residence = residence;
    }

    public UserEntity() {}
}
