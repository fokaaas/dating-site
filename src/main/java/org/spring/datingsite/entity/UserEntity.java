package org.spring.datingsite.entity;

import java.time.LocalDate;

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

    // Getters
    public String getId() {
        return id;
    }

    public String getSession() {
        return session;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSex() {
        return sex;
    }

    public String getPhoto() {
        return photo;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Integer getAge() {
        return age;
    }

    public String getResidence() {
        return residence;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public UserEntity() {}


}
