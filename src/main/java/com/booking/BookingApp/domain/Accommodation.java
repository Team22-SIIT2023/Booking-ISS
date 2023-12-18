package com.booking.BookingApp.domain;

import com.booking.BookingApp.domain.enums.AccommodationStatus;
import com.booking.BookingApp.domain.enums.AccommodationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "accommodations")
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Address address;

    @Column(name = "min_guest")
    private int minGuests;

    @Column(name = "max_guest")
    private int maxGuests;

    @Enumerated(EnumType.STRING)
    @Column(name = "acc_type")
    private AccommodationType type;

    @Column(name = "price_per_guest")
    private boolean pricePerGuest;

    @Column(name = "automatic_conf")
    private boolean automaticConfirmation;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private Host host;

    @Enumerated(EnumType.STRING)
    @Column(name = "acc_status")
    private AccommodationStatus status;

    @Column(name = "reservation_deadline")
    private int reservationDeadline;


    @OneToMany(cascade = {CascadeType.ALL},orphanRemoval = true)
    private Collection<TimeSlot> freeTimeSlots;


    @ManyToMany
    @JoinTable(name = "amenities_accommodation",
            joinColumns = @JoinColumn(name = "accommodation_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id"))
    private Collection<Amenity> amenities;


    @OneToMany(cascade = {CascadeType.ALL},orphanRemoval = true)
    private Collection<PricelistItem> priceList;

    @ElementCollection
    private List<String> images;


    public Accommodation(Long id, String name, String description, Address address, int minGuests, int maxGuests,
                         AccommodationType type, boolean pricePerGuest, boolean automaticConfirmation, Host host,
                         AccommodationStatus status, int reservationDeadline, Collection<TimeSlot> freeTimeSlots,
                         Collection<Amenity> amenities, Collection<PricelistItem> priceList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        this.pricePerGuest = pricePerGuest;
        this.automaticConfirmation = automaticConfirmation;
        this.host = host;
        this.status = status;
        this.reservationDeadline = reservationDeadline;
        this.freeTimeSlots = freeTimeSlots;
        this.amenities = amenities;
        this.priceList = priceList;
    }


    public Accommodation(Long id, String name, String description, Address address, int minGuests, int maxGuests,
                         AccommodationType type, boolean pricePerGuest, boolean automaticConfirmation, Host host,
                         AccommodationStatus status, int reservationDeadline, Collection<TimeSlot> freeTimeSlots,
                         Collection<Amenity> amenities, Collection<PricelistItem> priceList, List<String> images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        this.pricePerGuest = pricePerGuest;
        this.automaticConfirmation = automaticConfirmation;
        this.host = host;
        this.status = status;
        this.reservationDeadline = reservationDeadline;
        this.freeTimeSlots = freeTimeSlots;
        this.amenities = amenities;
        this.priceList = priceList;
        this.images = images;
    }

    @Override
    public String toString() {
        return "Accommodation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address=" + address +
                ", minGuests=" + minGuests +
                ", maxGuests=" + maxGuests +
                ", type=" + type +
                ", pricePerGuest=" + pricePerGuest +
                ", automaticConfirmation=" + automaticConfirmation +
                ", host='" + host + '\'' +
                ", status=" + status +
                ", reservationDeadline=" + reservationDeadline +
                ", freeTimeSlots=" + freeTimeSlots +
                ", amenities=" + amenities +
                ", priceList=" + priceList +
                '}';
    }


    public void setImage(String image) {
        this.images.add(image);
    }
}
