package com.booking.BookingApp.domain;

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
public class Accommodation {
    private Long id;
    private String name;
    private String description;
    private Address address;
    private ArrayList<Image>images;
    private int minGuests;
    private int maxGuests;
    private AccommodationType type;
    private boolean pricePerGuest;
    private boolean automaticConfirmation;
    private Long hostId;
    private AccommodationStatus status;
    private int reservationDeadline;
    private ArrayList<TimeSlot> freeTimeSlots;
    private ArrayList<Amenity> amenities;
    private ArrayList<PricelistItem> priceList;

    public Accommodation(Long id, String name, String description, Address address, ArrayList<Image> images, int minGuests, int maxGuests, AccommodationType type, boolean pricePerGuest, boolean automaticConfirmation, Long hostId, AccommodationStatus status, int reservationDeadline, ArrayList<TimeSlot> freeTimeSlots, ArrayList<Amenity> amenities, ArrayList<PricelistItem> priceList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.images = images;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        this.pricePerGuest = pricePerGuest;
        this.automaticConfirmation = automaticConfirmation;
        this.hostId = hostId;
        this.status = status;
        this.reservationDeadline = reservationDeadline;
        this.freeTimeSlots = freeTimeSlots;
        this.amenities = amenities;
        this.priceList = priceList;
    }

    @Override
    public String toString() {
        return "Accommodation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address=" + address +
                ", images=" + images +
                ", minGuests=" + minGuests +
                ", maxGuests=" + maxGuests +
                ", type=" + type +
                ", pricePerGuest=" + pricePerGuest +
                ", automaticConfirmation=" + automaticConfirmation +
                ", hostId='" + hostId + '\'' +
                ", status=" + status +
                ", reservationDeadline=" + reservationDeadline +
                ", freeTimeSlots=" + freeTimeSlots +
                ", amenities=" + amenities +
                ", priceList=" + priceList +
                '}';
    }
    public void copyValues(Accommodation accommodation) {
        this.id = accommodation.getId();
        this.name = accommodation.getName();
        this.description = accommodation.getDescription();
        this.address = accommodation.getAddress();
        this.images = new ArrayList<>(accommodation.getImages());
        this.minGuests = accommodation.getMinGuests();
        this.maxGuests = accommodation.getMaxGuests();
        this.type = accommodation.getType();
        this.pricePerGuest = accommodation.isPricePerGuest();
        this.automaticConfirmation = accommodation.isAutomaticConfirmation();
        this.hostId = accommodation.getHostId();
        this.status = accommodation.getStatus();
        this.reservationDeadline = accommodation.getReservationDeadline();
        this.freeTimeSlots = new ArrayList<>(accommodation.getFreeTimeSlots());
        this.amenities = new ArrayList<>(accommodation.getAmenities());
        this.priceList = new ArrayList<>(accommodation.getPriceList());
    }
}
