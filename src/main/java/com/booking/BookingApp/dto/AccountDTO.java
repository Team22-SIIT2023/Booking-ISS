package com.booking.BookingApp.dto;

import com.booking.BookingApp.domain.Role;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.domain.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
    private Long id;
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String username;
    @NotEmpty
    private String password;
    private Status status;
    @NotNull
    private List<Role> roles;

    public AccountDTO(Long id, String email, String password, Status status, List<Role> roles) {
        this.id=id;
        this.username = email;
        this.password = password;
        this.status = status;
        this.roles = roles;
    }

    public AccountDTO( String email, String password, Status status, List<Role> roles) {
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
