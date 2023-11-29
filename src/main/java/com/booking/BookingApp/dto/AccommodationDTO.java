package com.booking.BookingApp.dto;

import com.booking.BookingApp.domain.Address;
import com.booking.BookingApp.domain.Amenity;
import com.booking.BookingApp.domain.PricelistItem;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.domain.enums.AccommodationStatus;
import com.booking.BookingApp.domain.enums.AccommodationType;
import com.booking.BookingApp.domain.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class AccommodationDTO {

    private Long id;
    private String name;
    private String description;
    private AddressDTO address;
    private int minGuests;
    private int maxGuests;
    private AccommodationType type;
    private boolean pricePerGuest;
    private boolean automaticConfirmation;
    private Long hostId;
    private AccommodationStatus status;
    private int reservationDeadline;
    private ArrayList<AmenityDTO> amenities;
    private ArrayList<PricelistItem> priceList;
    private ArrayList<TimeSlot> freeTimeSlots;

    public AccommodationDTO(Long id, String name, String description, AddressDTO address, int minGuests, int maxGuests, AccommodationType type, boolean pricePerGuest, boolean automaticConfirmation, Long hostId,
                            AccommodationStatus status, int reservationDeadline, ArrayList<AmenityDTO> amenities,
                            ArrayList<PricelistItem> priceList,ArrayList<TimeSlot> freeTimeSlots) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        this.pricePerGuest = pricePerGuest;
        this.automaticConfirmation = automaticConfirmation;
        this.hostId = hostId;
        this.status = status;
        this.reservationDeadline = reservationDeadline;
        this.amenities = amenities;
        this.priceList=priceList;
        this.freeTimeSlots=freeTimeSlots;
    }

    @Override
    public String toString() {
        return "AccommodationDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address=" + address +
                ", minGuests=" + minGuests +
                ", maxGuests=" + maxGuests +
                ", type=" + type +
                ", pricePerGuest=" + pricePerGuest +
                ", automaticConfirmation=" + automaticConfirmation +
                ", hostId=" + hostId +
                ", status=" + status +
                ", reservationDeadline=" + reservationDeadline +
                ", amenities=" + amenities +
                ", priceList=" + priceList +
                ", freeTimeSlots=" + freeTimeSlots +
                '}';
    }
}
