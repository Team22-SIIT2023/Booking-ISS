package com.booking.BookingApp.dto;

import com.booking.BookingApp.domain.enums.AccommodationType;
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
    private AddressDTO address;
    private AccommodationType type;

    public AccommodationDTO(Long id, String name, String description, AddressDTO address, AccommodationType type) {
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
