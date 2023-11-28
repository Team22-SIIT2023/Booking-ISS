package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Comments;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.service.interfaces.ICommentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class CommentService implements ICommentService {

    @Override
    public Collection<Comments> findAll(Status status) {
        return data();
    }

    @Override
    public Comments findById(Long id) {
        return new Comments(1L, "Great comment!", LocalDate.now(), 4.5, Status.ACTIVE, null);
    }

    @Override
    public Collection<Comments> findByHostId(Long id, Status status) {
        return data();
    }

    @Override
    public Collection<Comments> findByAccommodationId(Long id, Status status) {
        return data();
    }

    @Override
    public int findHostRating(Long id) {
        return 3;
    }

    @Override
    public int findAccommodationRating(Long id) {
        return 5;
    }

    @Override
    public Comments createHostComment(Comments comment) {
        return new Comments(1L, "Great comment!", LocalDate.now(), 4.5, Status.ACTIVE, null);
    }

    @Override
    public Comments createAccommodationComment(Comments comment) {
        return new Comments(1L, "Great comment!", LocalDate.now(), 4.5, Status.ACTIVE, null);
    }

    @Override
    public Comments update(Comments comment) {
        return new Comments(1L, "Great comment!", LocalDate.now(), 4.5, Status.ACTIVE, null);
    }

    @Override
    public void delete(Long id) {}

    public Collection<Comments> data() {
        Collection<Comments> commentsList = new ArrayList<>();

        // Adding instances to the collection
        commentsList.add(new Comments(1L, "Great comment!", LocalDate.now(), 4.5, Status.ACTIVE, null));
        commentsList.add(new Comments(2L, "Interesting thoughts.", LocalDate.now(), 3.0, Status.REPORTED, null));
        commentsList.add(new Comments(3L, "Disagree with this.", LocalDate.now(), 2.5, Status.ACTIVE,null));

        return commentsList;
    }
}
