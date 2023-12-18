package com.booking.BookingApp.domain;

import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.domain.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;


    public Account(Long id, String username, String password, Status status, List<Role> roles) {
        this.id = id;
        this.username =username;
        this.password = password;
        this.status = status;
        this.roles = roles;
    }

    public Account(String username, String password, Status status, List<Role> roles) {
        this.username =username;
        this.password = password;
        this.status = status;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Account{" +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", role=" + roles +
                '}';
    }
}
