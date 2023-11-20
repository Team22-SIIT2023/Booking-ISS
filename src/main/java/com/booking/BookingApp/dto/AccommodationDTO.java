package com.booking.BookingApp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccommodationDTO {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String type;

    public AccommodationDTO(Long id, String name, String description, String address, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.type = type;
    }

    @Override
    public String toString() {
        return "AccommodationDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
