package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Comments;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.dto.CommentsDTO;

import java.util.Collection;


public interface ICommentService {

    Collection<Comments> findAll(Status status);

    Comments findById(Long id);

    Collection<Comments> findByHostId(Long id, Status status);

    Collection<Comments> findByAccommodationId(Long id, Status status);

    int findHostRating(Long id);

    int findAccommodationRating(Long id);

    Comments createHostComment(Comments comment, Long id);

    Comments createAccommodationComment(Comments comment, Long id);

    Comments update(Comments commentForUpdate,  Comments comment);

    void delete(Long id);
}