package com.mper.smartschool.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken extends BaseEntity {

    public static final int EXPIRATION = 7; // Minutes

    @Column(name = "token")
    private String token;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @OneToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;

    public PasswordResetToken() {
    }

    @Builder
    public PasswordResetToken(Long id, String token, LocalDateTime expiryDate, User user) {
        super(id);
        this.token = token;
        this.expiryDate = expiryDate;
        this.user = user;
    }
}
