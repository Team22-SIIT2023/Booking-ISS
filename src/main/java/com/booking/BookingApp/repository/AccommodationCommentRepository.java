package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.AccommodationComments;
import com.booking.BookingApp.domain.Comments;
import com.booking.BookingApp.domain.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface AccommodationCommentRepository extends JpaRepository<AccommodationComments,Long> {
    Collection<Comments> findAllByAccommodationIdAndStatus(Long id, Status status);
}