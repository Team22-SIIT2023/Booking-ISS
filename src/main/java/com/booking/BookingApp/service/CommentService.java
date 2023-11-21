package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Comments;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.service.interfaces.ICommentService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CommentService implements ICommentService {

    @Override
    public Collection<Comments> findAll() {
        return null;
    }

    @Override
    public Comments findById(Long id) {
        return null;
    }

    @Override
    public Collection<Comments> findByHostId(Long id) {
        return null;
    }

    @Override
    public Collection<Comments> findByAccommodationId(Long id) {
        return null;
    }

    @Override
    public Collection<Comments> findByStatus(Status status) {return null;}

    @Override
    public int findHostRating(Long id) {
        return 0;
    }

    @Override
    public int findAccommodationRating(Long id) {
        return 0;
    }

    @Override
    public Comments create(Comments comment) {
        return null;
    }

    @Override
    public Comments update(Comments comment) {
        return null;
    }

    @Override
    public void delete(Long id) {}
}
