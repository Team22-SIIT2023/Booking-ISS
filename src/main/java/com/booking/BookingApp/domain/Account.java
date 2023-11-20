package com.booking.BookingApp.domain;

import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.domain.enums.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Account {
    private String email;
    private String password;
    private Status status;
    private UserType type;

    public Account(String email, String password, Status status, UserType type) {
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
