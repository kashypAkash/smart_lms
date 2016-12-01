package com.lms.cmpe.model;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private int universityId;
    private String userRole = "USER";
    private String email;
    private String password;
    private String verificationCode;
    private boolean isVerified;

    public User(){}

    public User(int universityId, String userRole, String email, String password, String verificationCode, boolean isVerified) {
        this.universityId = universityId;
        this.userRole = userRole;
        this.email = email;
        this.password = password;
        this.verificationCode = verificationCode;
        this.isVerified = isVerified;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUniversityId() {
        return universityId;
    }

    public void setUniversityId(int universityId) {
        this.universityId = universityId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", universityId=" + universityId +
                ", userRole=" + userRole +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                ", isVerified=" + isVerified +
                '}';
    }
}
