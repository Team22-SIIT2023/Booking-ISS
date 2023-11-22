package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Comments;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.dto.CommentsDTO;
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

@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    @Autowired
    private CommentService commentService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CommentsDTO>> getComments() {
        Collection<CommentsDTO> comments = commentService.findAll();
        return new ResponseEntity<Collection<CommentsDTO>>(comments,HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentsDTO> getById(@PathVariable("id") Long id) {
        CommentsDTO comment = commentService.findById(id);
        return new ResponseEntity<CommentsDTO>(comment,HttpStatus.OK);
    }

    @GetMapping(value = "/host", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CommentsDTO>> getHostComments(@RequestParam("hostId") Long id) {
        Collection<CommentsDTO> comments = commentService.findByHostId(id);
        return new ResponseEntity<Collection<CommentsDTO>>(comments,HttpStatus.OK);
    }

    @GetMapping(value = "/accommodation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CommentsDTO>> getAccommodationComments(@RequestParam("accommodationId") Long id) {
        Collection<CommentsDTO> comments = commentService.findByAccommodationId(id);
        return new ResponseEntity<Collection<CommentsDTO>>(comments,HttpStatus.OK);
    }

    @GetMapping(value = "/status",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CommentsDTO>> getByStatus(@RequestParam("status") Status status) {
        Collection<CommentsDTO> comments = commentService.findByStatus(status);
        return new ResponseEntity<Collection<CommentsDTO>>(comments,HttpStatus.OK);
    }

    @GetMapping(value = "/hostRating", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getHostRating(@RequestParam("idForHostRating") Long id) {
        int rating = commentService.findHostRating(id);
        return new ResponseEntity<Integer>(rating,HttpStatus.OK);
    }

    @GetMapping(value = "/accommodationRating", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getAccommodationRating(@RequestParam("idForAccommodationRating") Long id) {
        int rating = commentService.findAccommodationRating(id);
        return new ResponseEntity<Integer>(rating,HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentsDTO> createComment(@RequestBody CommentsDTO comment) {
        CommentsDTO savedComment = commentService.create(comment);
        return new ResponseEntity<CommentsDTO>(savedComment, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentsDTO> updateComment(@PathVariable("id") Long id) {
        CommentsDTO commentForUpdated = commentService.findById(id);
        // zavrsiti !!!!!
        return new ResponseEntity<CommentsDTO>(commentForUpdated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CommentsDTO> deleteRequest(@PathVariable("id") Long id) {
        commentService.delete(id);
        return new ResponseEntity<CommentsDTO>(HttpStatus.NO_CONTENT);
    }
}