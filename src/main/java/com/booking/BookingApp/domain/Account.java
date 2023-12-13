package com.booking.BookingApp.domain;

import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.domain.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Role role;

    public Account(Long id, String username, String password, Status status, Role role) {
        this.id = id;
        this.username =username;
        this.password = password;
        this.status = status;
        this.role = role;
    }

    public Account(String username, String password, Status status, Role role) {
        this.username =username;
        this.password = password;
        this.status = status;
        this.role = role;
    }

    @Override
    public String toString() {
        return "Account{" +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", role=" + role +
                '}';
    }
}
