package com.booking.BookingApp.dto;

import com.booking.BookingApp.domain.Role;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.domain.enums.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
    private String username;
    private String password;
    private Status status;
    private Role role;

    public AccountDTO(String email, String password, Status status, Role role) {
        this.username = email;
        this.password = password;
        this.status = status;
        this.role = role;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "email='" + username + '\'' +
                ", password=" + password +
                ", status=" + status +
                ", role=" + role +
                '}';
    }
}
