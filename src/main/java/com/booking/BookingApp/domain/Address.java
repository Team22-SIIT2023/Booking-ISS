package com.booking.BookingApp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "address")
    private String address;

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public Address(Long id, String country, String city, String postalCode, String address) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.address = address;
    }

    public Address(String country, String city, String postalCode, String address) {
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.address = address;
    }
}
