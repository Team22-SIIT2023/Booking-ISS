package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    Collection<Accommodation> findAllByHost(Host host);
}
