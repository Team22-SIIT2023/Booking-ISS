package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Comments;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.dto.CommentsDTO;
import com.booking.BookingApp.dto.RequestDTO;
import com.booking.BookingApp.service.interfaces.ICommentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class CommentService implements ICommentService {

    @Override
    public Collection<CommentsDTO> findAll() {
        return data();
    }

    @Override
    public CommentsDTO findById(Long id) {
        return new CommentsDTO(1L, "Great comment!", LocalDate.now(), 4.5, Status.ACTIVE, null);
    }

    @Override
    public Collection<CommentsDTO> findByHostId(Long id) {
        return data();
    }

    @Override
    public Collection<CommentsDTO> findByAccommodationId(Long id) {
        return data();
    }

    @Override
    public Collection<CommentsDTO> findByStatus(Status status) {
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
    public CommentsDTO create(CommentsDTO comment) {

        return new CommentsDTO(1L, "Great comment!", LocalDate.now(), 4.5, Status.ACTIVE, null);
    }

    @Override
    public CommentsDTO update(CommentsDTO comment) {
        return new CommentsDTO(1L, "Great comment!", LocalDate.now(), 4.5, Status.ACTIVE, null);
    }

    @Override
    public void delete(Long id) {}

    public Collection<CommentsDTO> data() {
        Collection<CommentsDTO> commentsList = new ArrayList<>();

        // Adding instances to the collection
        commentsList.add(new CommentsDTO(1L, "Great comment!", LocalDate.now(), 4.5, Status.ACTIVE, null));
        commentsList.add(new CommentsDTO(2L, "Interesting thoughts.", LocalDate.now(), 3.0, Status.REPORTED, null));
        commentsList.add(new CommentsDTO(3L, "Disagree with this.", LocalDate.now(), 2.5, Status.ACTIVE,null));

        return commentsList;
    }
}
