package com.technation.technation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.time.LocalDateTime;

@Entity
@Table(name = "unverified_user")
public class UnverifiedUser {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column(name = "name")
    private String name;
    @Email
    @Column(name = "email")
    private String email;
    @Column(name = "password_hash")
    private String password;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "address")
    private String address;
    @Column(name = "phone_no")
    private String phoneNo;

    @OneToOne(mappedBy = "unverifiedUser", cascade = CascadeType.ALL)
    private VerificationCode verificationCode;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public VerificationCode getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(VerificationCode verificationCode) {
        this.verificationCode = verificationCode;
    }
}
