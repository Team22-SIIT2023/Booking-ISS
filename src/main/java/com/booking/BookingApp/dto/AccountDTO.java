package com.booking.BookingApp.dto;

import com.booking.BookingApp.domain.Role;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.domain.enums.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
    private String username;
    private String password;
    private Status status;
    private List<Role> roles;

    public AccountDTO(String email, String password, Status status, List<Role> roles) {
        this.username = email;
        this.password = password;
        this.status = status;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "email='" + username + '\'' +
                ", password=" + password +
                ", status=" + status +
                ", role=" + roles +
                '}';
    }
}
