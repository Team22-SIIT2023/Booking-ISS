package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Comments;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.dto.CommentsDTO;

import java.util.Collection;


public interface ICommentService {

    Collection<CommentsDTO> findAll();

    CommentsDTO findById(Long id);

    Collection<CommentsDTO> findByHostId(Long id);

    Collection<CommentsDTO> findByAccommodationId(Long id);

    Collection<CommentsDTO> findByStatus(Status status);

    int findHostRating(Long id);

    int findAccommodationRating(Long id);

    CommentsDTO create(CommentsDTO comment);

    CommentsDTO update(CommentsDTO comment);

    void delete(Long id);
}
