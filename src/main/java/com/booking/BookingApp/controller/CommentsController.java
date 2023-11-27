package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Comments;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.dto.CommentsDTO;
import com.booking.BookingApp.dto.RequestDTO;
import com.booking.BookingApp.mapper.CommentsDTOMapper;
import com.booking.BookingApp.mapper.RequestDTOMapper;
import com.booking.BookingApp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    @Autowired
    private CommentService commentService;

    // ako se ne prosledi status onda se svi prikazuju
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CommentsDTO>> getComments(@RequestParam(value="status", required = false) Status status) {
        Collection<Comments> comments = commentService.findAll(status);

        Collection<CommentsDTO> commentsDTO = comments.stream()
                .map(CommentsDTOMapper::fromCommentstoDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<Collection<CommentsDTO>>(commentsDTO,HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentsDTO> getById(@PathVariable("id") Long id) {
        Comments comment = commentService.findById(id);
        return new ResponseEntity<CommentsDTO>(new CommentsDTO(comment), HttpStatus.OK);
    }

    @GetMapping(value = "/host/{hostId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CommentsDTO>> getHostComments(@PathVariable("hostId") Long id) {
        Collection<Comments> comments = commentService.findByHostId(id);

        Collection<CommentsDTO> commentsDTO = comments.stream()
                .map(CommentsDTOMapper::fromCommentstoDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<Collection<CommentsDTO>>(commentsDTO,HttpStatus.OK);
    }

    @GetMapping(value = "/accommodation/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CommentsDTO>> getAccommodationComments(@PathVariable("accommodationId") Long id) {
        Collection<Comments> comments = commentService.findByAccommodationId(id);

        Collection<CommentsDTO> commentsDTO = comments.stream()
                .map(CommentsDTOMapper::fromCommentstoDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<Collection<CommentsDTO>>(commentsDTO,HttpStatus.OK);
    }

//    Izbrisati i dodati query param status kada se getuju svi komentari
//    @GetMapping(value = "/status",produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Collection<CommentsDTO>> getByStatus(@RequestParam("status") Status status) {
//        Collection<CommentsDTO> comments = commentService.findByStatus(status);
//        return new ResponseEntity<Collection<CommentsDTO>>(comments,HttpStatus.OK);
//    }

    @GetMapping(value = "/host/{hostId}/averageRate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getHostRating(@PathVariable("hostId") Long id) {
        int rating = commentService.findHostRating(id);
        return new ResponseEntity<Integer>(rating,HttpStatus.OK);
    }

        @GetMapping(value = "/accommodation/{accommodationId}/averageRate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getAccommodationRating(@PathVariable("accommodationId") Long id) {
        int rating = commentService.findAccommodationRating(id);
        return new ResponseEntity<Integer>(rating,HttpStatus.OK);
    }

//    @GetMapping(value = "/accommodationRating", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Integer> getAccommodationRating(@RequestParam("idForAccommodationRating") Long id) {
//        int rating = commentService.findAccommodationRating(id);
//        return new ResponseEntity<Integer>(rating,HttpStatus.OK);
//    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentsDTO> createComment(@RequestBody CommentsDTO commentDTO) {
        Comments commentModel = CommentsDTOMapper.fromDTOtoComments(commentDTO);
        Comments savedComment = commentService.create(commentModel);
        return new ResponseEntity<CommentsDTO>(new CommentsDTO(savedComment), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentsDTO> updateComment(@RequestBody CommentsDTO comment,@PathVariable("id") Long id) {
        Comments commentForUpdate = commentService.findById(id);
        commentForUpdate.setDate(comment.getDate());
        commentForUpdate.setGuest(comment.getGuest());
        commentForUpdate.setStatus(comment.getStatus());
        commentForUpdate.setText(comment.getText());
        Comments updatedComment = commentService.update(commentForUpdate);
        // proveriti jel ok!
        return new ResponseEntity<CommentsDTO>(new CommentsDTO(updatedComment), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CommentsDTO> deleteRequest(@PathVariable("id") Long id) {
        commentService.delete(id);
        return new ResponseEntity<CommentsDTO>(HttpStatus.NO_CONTENT);
    }
}