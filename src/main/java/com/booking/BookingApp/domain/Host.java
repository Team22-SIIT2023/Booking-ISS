package com.booking.BookingApp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "hosts")
public class Host extends User {

    public Host(Long id, String firstName, String lastName, Address address, String phoneNumber, Account account, String picturePath) {
        super(id, firstName, lastName, address, phoneNumber, account, picturePath);
    }
}
