package com.booking.BookingApp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//@MappedSuperclass
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Address address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(cascade = {CascadeType.ALL})
    private Account account;

    @Column(name = "picture_path")
    private String picturePath;

    public User(Long id, String firstName, String lastName, Address address, String phoneNumber, Account account, String picturePath) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.account = account;
        this.picturePath = picturePath;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", account=" + account +
                ",picturePath" + picturePath +
                '}';
    }
}
