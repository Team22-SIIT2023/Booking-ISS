package com.booking.BookingApp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "amenities")
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="amenity_name")
    private String name;
//    private ImageIcon icon;

    public Amenity(Long id, String name) {
        this.id = id;
        this.name = name;
//        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Amenity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
