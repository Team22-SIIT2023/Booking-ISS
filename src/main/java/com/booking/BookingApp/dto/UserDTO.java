package com.booking.BookingApp.dto;

import com.booking.BookingApp.domain.Address;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotNull
    private AddressDTO address;
    @NotEmpty
    private String phoneNumber;
    private AccountDTO account;
    private String picturePath;
    private String reportingReason;


    public UserDTO(Long id, String firstName, String lastName, AddressDTO address, String phoneNumber, AccountDTO accountDTO, String picturePath, String reportingReason) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.account = accountDTO;
        this.picturePath = picturePath;
        this.reportingReason = reportingReason;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", addressDTO=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", accountDTO=" + account +
                ", picturePath=" + picturePath +
                '}';
    }
}
