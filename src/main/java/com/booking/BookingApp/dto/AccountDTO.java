package com.booking.BookingApp.dto;

import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.domain.enums.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
    private String email;
    private Status status;
    private UserType type;

    public AccountDTO(String email, Status status, UserType type) {
        this.email = email;
        this.status = status;
        this.type = type;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "email='" + email + '\'' +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
