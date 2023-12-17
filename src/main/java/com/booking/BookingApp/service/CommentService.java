package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.*;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.repository.AccommodationCommentRepository;
import com.booking.BookingApp.service.interfaces.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CommentService implements ICommentService {
    @Autowired
    AccommodationCommentRepository accommodationCommentRepository;

    @Override
    public Collection<Comments> findAll(Status status) {
        return data();
    }

    @Override
    public Comments findById(Long id) {
        return new Comments(1L, "Great comment!", LocalDate.now(), 4.5, Status.ACTIVE, null, false);
    }

    @Override
    public Collection<Comments> findByHostId(Long id, Status status) {
        return data();
    }

    @Override
    public Collection<Comments> findByAccommodationId(Long id, Status status) {

        return accommodationCommentRepository.findAllByAccommodationIdAndStatus(id,status);
    }

    @Override
    public int findHostRating(Long id) {
        return 3;
    }

    @Override
    public double findAccommodationRating(Long id) {
        Collection<Comments> commentsAndRatings= findByAccommodationId(id,Status.ACTIVE);
        double sum=0;
        for (Comments comment:commentsAndRatings){
            sum+=comment.getRating();
        }
        if(sum!=0){
            return (double) (sum/commentsAndRatings.size());
        }
        return 0;

    }

    @Override
    public Comments createHostComment(Comments comment, Long id) {
        return new Comments(1L, "Great comment!", LocalDate.now(), 4.5, Status.ACTIVE, null, false);
    }

    @Override
    public Comments createAccommodationComment(Comments comment, Long id) {
        return new Comments(1L, "Great comment!", LocalDate.now(), 4.5, Status.ACTIVE, null, false);
    }

    @Override
    public Comments update(Comments commentForUpdate,  Comments comment) {
        return new Comments(1L, "Great comment!", LocalDate.now(), 4.5, Status.ACTIVE, null, false);
    }

    @Override
    public void delete(Long id) {}

    public Collection<Comments> data() {
        Collection<Comments> commentsList = new ArrayList<>();
        Address address = new Address("Srbija","Novi Sad","21000","Futoska 1",false);
        Role role=new Role(1L,"guest");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        Account account = new Account(1L, "aleksicisidora@yahoo.com","slatkica",Status.ACTIVE, roles, false);
        Guest guest = new Guest(1L,"Isidora","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg",false, null);
        // Adding instances to the collection
        commentsList.add(new Comments(1L, "Great comment!", LocalDate.now(), 4.5, Status.ACTIVE, guest, false));
        commentsList.add(new Comments(2L, "Agree with you.", LocalDate.now(), 3.0, Status.REPORTED, guest,false));
        commentsList.add(new Comments(3L, "Disagree with this.", LocalDate.now(), 2.5, Status.ACTIVE,guest, false));
        commentsList.add(new Comments(4L, "Fantastic!", LocalDate.now(), 4.5, Status.ACTIVE, guest,false));
        commentsList.add(new Comments(5L, "Nothing special.", LocalDate.now(), 3.0, Status.REPORTED, guest, false));
        commentsList.add(new Comments(6L, "I don't like it.", LocalDate.now(), 2.5, Status.ACTIVE,guest, false));
        commentsList.add(new Comments(7L, "It was good!", LocalDate.now(), 4.5, Status.ACTIVE, guest,false));
        commentsList.add(new Comments(8L, "It's okay for that price.", LocalDate.now(), 3.0, Status.REPORTED, guest,false));
        commentsList.add(new Comments(9L, "It was dirty.", LocalDate.now(), 2.5, Status.ACTIVE,guest,false));
        commentsList.add(new Comments(10L, "The host is extra.", LocalDate.now(), 4.5, Status.ACTIVE, guest,false));
        commentsList.add(new Comments(11L, "Not good not bad.", LocalDate.now(), 3.0, Status.REPORTED, guest,false));
        commentsList.add(new Comments(12L, "Bad.", LocalDate.now(), 2.5, Status.ACTIVE,guest,false));
        commentsList.add(new Comments(13L, "Super!", LocalDate.now(), 4.5, Status.ACTIVE, guest,false));
        commentsList.add(new Comments(14L, "Fun but I expected more.", LocalDate.now(), 3.0, Status.REPORTED, guest,false));
        commentsList.add(new Comments(15L, "I'm not returning there.", LocalDate.now(), 2.5, Status.ACTIVE,guest,false));
        return commentsList;
    }
}
