package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.User;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.dto.UserDTO;
import com.booking.BookingApp.mapper.UserDTOMapper;
import com.booking.BookingApp.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<UserDTO>> getUsers() {
        Collection<User> users = userService.findAll();
        Collection<UserDTO> usersDTO = users.stream()
                .map(UserDTOMapper::fromUsertoDTO)
                .toList();
        return new ResponseEntity<Collection<UserDTO>>(usersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
        User user = userService.findOne(id);
        if (user == null) {
            return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserDTO>(UserDTOMapper.fromUsertoDTO(user), HttpStatus.OK);
    }

    @GetMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<UserDTO>> getUsersByStatus(@RequestParam("status") Status status) {
        Collection<User> users = userService.findAllByStatus(status);
        Collection<UserDTO> usersDTO = users.stream()
                .map(UserDTOMapper::fromUsertoDTO)
                .toList();
        return new ResponseEntity<Collection<UserDTO>>(usersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/userEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam("userEmail") String userEmail) {
        User user = userService.findOneByEmail(userEmail);
        if (user == null) {
            return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserDTO>(UserDTOMapper.fromUsertoDTO(user), HttpStatus.OK);
    }

    @PostMapping("/user-account-activation/{id}")
    public ResponseEntity<String> activateUser(@PathVariable Long id){
        boolean isActivated = userService.activateUser(id);
        if(isActivated){
            return new ResponseEntity<String>("User account is successfully activated.",HttpStatus.OK);
        }
        return new ResponseEntity<String>("Failed to activate user account.",HttpStatus.BAD_REQUEST);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) throws Exception {
        User newUser = UserDTOMapper.fromDTOtoUser(userDTO);
        User savedUser = userService.create(newUser);
        return new ResponseEntity<UserDTO>(UserDTOMapper.fromUsertoDTO(savedUser), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO useDTO, @PathVariable Long id)
            throws Exception {
        User userForUpdate = userService.findOne(id);
        User updatedUser = userService.update(userForUpdate);
        if (updatedUser == null) {
            return new ResponseEntity<UserDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<UserDTO>(UserDTOMapper.fromUsertoDTO(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);
    }




}
