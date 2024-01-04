package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.Comments;
import com.booking.BookingApp.domain.HostComments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface HostCommentRepository extends JpaRepository<HostComments,Long> {
    Collection<Comments> findByHost_Id(Long id);
}
