package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
