package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Comments;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.dto.CommentsDTO;

import java.util.Collection;


public interface ICommentService {

    Collection<Comments> findAll(Status status);

    Comments findById(Long id);

    Collection<Comments> findByHostId(Long id);

    Collection<Comments> findByAccommodationId(Long id);

    Collection<Comments> findByStatus(Status status);

    int findHostRating(Long id);

    int findAccommodationRating(Long id);

    Comments create(Comments comment);

    Comments update(Comments comment);

    void delete(Long id);
}
