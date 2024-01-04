package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Comments;
import com.booking.BookingApp.domain.Host;
import com.booking.BookingApp.domain.HostComments;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.dto.AccommodationDTO;
import com.booking.BookingApp.dto.CommentsDTO;
import com.booking.BookingApp.dto.CreateHostCommentDTO;
import com.booking.BookingApp.dto.RequestDTO;
import com.booking.BookingApp.mapper.AccommodationDTOMapper;
import com.booking.BookingApp.mapper.CommentsDTOMapper;
import com.booking.BookingApp.mapper.HostCommentDTOMapper;
import com.booking.BookingApp.mapper.RequestDTOMapper;
import com.booking.BookingApp.service.CommentService;
import com.booking.BookingApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST') or hasAuthority('ROLE_HOST') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Collection<CommentsDTO>> getComments(@RequestParam(value="status", required = false) Status status) {
        Collection<Comments> comments = commentService.findAll(status);

        Collection<CommentsDTO> commentsDTO = comments.stream()
                .map(CommentsDTOMapper::fromCommentstoDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<Collection<CommentsDTO>>(commentsDTO,HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST') or hasAuthority('ROLE_HOST')")
    public ResponseEntity<CommentsDTO> getById(@PathVariable("id") Long id) {
        Comments comment = commentService.findById(id);
        return new ResponseEntity<CommentsDTO>(new CommentsDTO(comment), HttpStatus.OK);
    }

    @GetMapping(value = "/host/{hostId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST') or hasAuthority('ROLE_HOST')")
    public ResponseEntity<Collection<CommentsDTO>> getHostComments(@PathVariable("hostId") Long id,
                                                                   @RequestParam(value = "status", required = false) Status status) {
        Collection<Comments> comments = commentService.findByHostId(id, status);

        Collection<CommentsDTO> commentsDTO = comments.stream()
                .map(CommentsDTOMapper::fromCommentstoDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<Collection<CommentsDTO>>(commentsDTO,HttpStatus.OK);
    }

    @GetMapping(value = "/accommodation/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST') or hasAuthority('ROLE_HOST') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Collection<CommentsDTO>> getAccommodationComments(@PathVariable("accommodationId") Long id,
                                                                            @RequestParam(value = "status", required = false) Status status) {
        Collection<Comments> comments = commentService.findByAccommodationId(id, status);

        Collection<CommentsDTO> commentsDTO = comments.stream()
                .map(CommentsDTOMapper::fromCommentstoDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<Collection<CommentsDTO>>(commentsDTO,HttpStatus.OK);
    }

    @GetMapping(value = "/host/{hostId}/averageRate", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST') or hasAuthority('ROLE_HOST')")
    public ResponseEntity<Double> getHostRating(@PathVariable("hostId") Long id) {
        double rating = commentService.findHostRating(id);
        return new ResponseEntity<Double>(rating,HttpStatus.OK);
    }

    @GetMapping(value = "/accommodation/{accommodationId}/averageRate", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST') or hasAuthority('ROLE_HOST') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Double> getAccommodationRating(@PathVariable("accommodationId") Long id) {
        double rating = commentService.findAccommodationRating(id);
        return new ResponseEntity<Double>(rating,HttpStatus.OK);
    }

    @PostMapping(value = "/host/{hostId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<CommentsDTO> createHostComment(@RequestBody CommentsDTO commentDTO,
                                                         @PathVariable("hostId") Long id) {
//        Host host = (Host) userService.findOne(id);
        Comments commentModel = CommentsDTOMapper.fromDTOtoComments(commentDTO);
        Comments savedComment = commentService.createHostComment(commentModel, id);
        if (savedComment==null) {
            return new ResponseEntity<CommentsDTO>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<CommentsDTO>(new CommentsDTO(savedComment), HttpStatus.CREATED);
    }

    @PostMapping(value = "/accommodation/{accommodationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<CommentsDTO> createAccommodationComment(@RequestBody CommentsDTO commentDTO,
                                                                  @PathVariable("accommodationId") Long id) {
        Comments commentModel = CommentsDTOMapper.fromDTOtoComments(commentDTO);
        Comments savedComment = commentService.createAccommodationComment(commentModel, id);
        if (savedComment==null) {
            return new ResponseEntity<CommentsDTO>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<CommentsDTO>(new CommentsDTO(savedComment), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentsDTO> updateComment(@RequestBody CommentsDTO commentDTO, @PathVariable("id") Long id) {
        Comments commentForUpdate = commentService.findById(id);
        Comments comment = CommentsDTOMapper.fromDTOtoComments(commentDTO);
        Comments updatedComment = commentService.update(commentForUpdate, comment);
        return new ResponseEntity<CommentsDTO>(new CommentsDTO(updatedComment), HttpStatus.OK);
    }

    @PutMapping(value = "reportComment/{commentId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<CommentsDTO> reportComment(@RequestBody Status status, @PathVariable("commentId") Long commentId) {
        Comments commentForUpdate = commentService.findById(commentId);
        Comments updatedComment = commentService.reportComment(commentForUpdate, status);
        return new ResponseEntity<CommentsDTO>(new CommentsDTO(updatedComment), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<CommentsDTO> deleteComment(@PathVariable("id") Long id) {
        commentService.delete(id);
        return new ResponseEntity<CommentsDTO>(HttpStatus.NO_CONTENT);
    }
}