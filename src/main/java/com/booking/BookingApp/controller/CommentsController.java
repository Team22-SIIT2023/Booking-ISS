package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Comments;
import com.booking.BookingApp.domain.enums.Status;
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
    public ResponseEntity<Collection<Comments>> getComments() {
        Collection<Comments> comments = commentService.findAll();
        return new ResponseEntity<Collection<Comments>>(comments,HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comments> getById(@PathVariable("id") Long id) {
        Comments comment = commentService.findById(id);
        return new ResponseEntity<Comments>(comment,HttpStatus.OK);
    }

    @GetMapping(value = "/host", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Comments>> getHostComments(@RequestParam("hostId") Long id) {
        Collection<Comments> comments = commentService.findByHostId(id);
        return new ResponseEntity<Collection<Comments>>(comments,HttpStatus.OK);
    }

    @GetMapping(value = "/accommodation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Comments>> getAccommodationComments(@RequestParam("accommodationId") Long id) {
        Collection<Comments> comments = commentService.findByAccommodationId(id);
        return new ResponseEntity<Collection<Comments>>(comments,HttpStatus.OK);
    }

    @GetMapping(value = "/status",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Comments>> getByStatus(@RequestParam("status") Status status) {
        Collection<Comments> comments = commentService.findByStatus(status);
        return new ResponseEntity<Collection<Comments>>(comments,HttpStatus.OK);
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
    public ResponseEntity<Comments> createComment(@RequestBody Comments comment) {
        Comments savedComment = commentService.create(comment);
        return new ResponseEntity<Comments>(savedComment, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comments> updateComment(@PathVariable("id") Long id) {
        Comments commentForUpdated = commentService.findById(id);
        // zavrsiti !!!!!
        return new ResponseEntity<Comments>(commentForUpdated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Comments> deleteRequest(@PathVariable("id") Long id) {
        commentService.delete(id);
        return new ResponseEntity<Comments>(HttpStatus.NO_CONTENT);
    }
}