package com.technation.technation.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class VerificationCode {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Column(name = "code")
    private String code;
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @OneToOne
    @JoinColumn(name = "unverified_user_id", referencedColumnName = "id")
    private UnverifiedUser unverifiedUser;

    @PrePersist
    public void onCreate(){
        this.expiresAt = LocalDateTime.now().plusMinutes(15);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public UnverifiedUser getUnverifiedUser() {
        return unverifiedUser;
    }

    public void setUnverifiedUser(UnverifiedUser unverifiedUser) {
        this.unverifiedUser = unverifiedUser;
    }
}
