package com.booking.BookingApp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
@NoArgsConstructor
public class AmenityDTO {
    private Long id;
    private String name;
    private ImageIcon icon;

    public AmenityDTO(Long id, String name, ImageIcon icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "AmenityDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon=" + icon +
                '}';
    }
}
