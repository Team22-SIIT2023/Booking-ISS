package com.booking.BookingApp.dto;

import com.booking.BookingApp.domain.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private AddressDTO addressDTO;
    private String phoneNumber;
    private AccountDTO accountDTO;
    private String picturePath;

    public UserDTO(Long id, String firstName, String lastName, AddressDTO address, String phoneNumber, AccountDTO accountDTO, String picturePath) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressDTO = address;
        this.phoneNumber = phoneNumber;
        this.accountDTO = accountDTO;
        this.picturePath = picturePath;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", addressDTO=" + addressDTO +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", accountDTO=" + accountDTO +
                ", picturePath=" + picturePath +
                '}';
    }
}
