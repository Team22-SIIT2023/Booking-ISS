package com.booking.BookingApp.dto;

import com.booking.BookingApp.domain.PricelistItem;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.domain.enums.AccommodationStatus;
import com.booking.BookingApp.domain.enums.AccommodationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class CreateAccommodationDTO {
    private String name;
    private String description;
    private AddressDTO address;
    private int minGuests;
    private int maxGuests;
    private AccommodationType type;
    private boolean pricePerGuest;
    private boolean automaticConfirmation;
    private Long hostId;
    private int reservationDeadline;
    private ArrayList<AmenityDTO> amenities;
    private ArrayList<PricelistItem> priceList;
    private ArrayList<TimeSlot> freeTimeSlots;

    public CreateAccommodationDTO(String name, String description, AddressDTO address, int minGuests, int maxGuests, AccommodationType type, boolean pricePerGuest, boolean automaticConfirmation, Long hostId,
                            int reservationDeadline, ArrayList<AmenityDTO> amenities,
                            ArrayList<PricelistItem> priceList,ArrayList<TimeSlot> freeTimeSlots) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        this.pricePerGuest = pricePerGuest;
        this.automaticConfirmation = automaticConfirmation;
        this.hostId = hostId;
        this.reservationDeadline = reservationDeadline;
        this.amenities = amenities;
        this.priceList=priceList;
        this.freeTimeSlots=freeTimeSlots;
    }

    @Override
    public String toString() {
        return "AccommodationDTO{" +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address=" + address +
                ", minGuests=" + minGuests +
                ", maxGuests=" + maxGuests +
                ", type=" + type +
                ", pricePerGuest=" + pricePerGuest +
                ", automaticConfirmation=" + automaticConfirmation +
                ", hostId=" + hostId +
                ", reservationDeadline=" + reservationDeadline +
                ", amenities=" + amenities +
                ", priceList=" + priceList +
                ", freeTimeSlots=" + freeTimeSlots +
                '}';
    }
}