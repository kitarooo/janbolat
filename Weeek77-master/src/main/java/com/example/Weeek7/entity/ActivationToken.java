package com.example.Weeek7.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "activation_token")
public class ActivationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    private String token;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public ActivationToken(User user) {
        this.token = UUID.randomUUID().toString();
        this.user = user;
        this.expiresAt = LocalDateTime.now().plusMinutes(20);
    }

    @Override
    public String toString() {
        return "ActivationToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user +
                ", createdAt=" + createdAt +
                ", expiresAt=" + expiresAt +
                '}';
    }
}
