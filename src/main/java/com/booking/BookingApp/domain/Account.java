package com.booking.BookingApp.domain;

import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.domain.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private UserType type;

    public Account(Long id, String email, String password, Status status, UserType type) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.status = status;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Account{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
