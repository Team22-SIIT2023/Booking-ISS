package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Report;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.domain.User;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.dto.*;
import com.booking.BookingApp.exception.ResourceConflictException;
import com.booking.BookingApp.mapper.AccommodationDTOMapper;
import com.booking.BookingApp.mapper.ReportDTOMapper;
import com.booking.BookingApp.mapper.UserDTOMapper;
import com.booking.BookingApp.service.interfaces.IUserService;
import com.booking.BookingApp.util.TokenUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

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
    @GetMapping(value = "/guest/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getGuestFavorites(@PathVariable("id") Long id) {
        Collection<Accommodation> accommodations= userService.findFavorites(id);
        if (accommodations == null) {
            return new ResponseEntity<Collection<AccommodationDTO>>(HttpStatus.NOT_FOUND);
        }
        Collection<AccommodationDTO> accommodationDTOS = accommodations.stream()
                .map(AccommodationDTOMapper::fromAccommodationtoDTO)
                .toList();
        return new ResponseEntity<Collection<AccommodationDTO>>(accommodationDTOS, HttpStatus.OK);

    }
    @PostMapping(value ="/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody UserCredentialsDTO userCredentials, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userCredentials.getUsername(), userCredentials.getPassword()));


        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    };
    @PostMapping("/signup")
    public ResponseEntity<User> addUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        User existUser = this.userService.findByUsername(user.getUsername());

        if (existUser != null) {
            throw new ResourceConflictException(user.getId(), "Username already exists");
        }

        User newUser = this.userService.save(user);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

//    @GetMapping(value ="/log-in", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<UserDTO> getReportByHostAndTimeSlot(
//            @RequestParam("username") String username,
//            @RequestParam("password") String password) {
//        User user = userService.findLoggedUser(username,password);
//        if (user == null) {
//            return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<UserDTO>(UserDTOMapper.fromUsertoDTO(user), HttpStatus.OK);
//    };

//    @GetMapping(value = "/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Collection<UserDTO>> getUsersByStatus(@PathVariable("status") Status status) {
//        Collection<User> users = userService.findAllByStatus(status);
//        Collection<UserDTO> usersDTO = users.stream()
//                .map(UserDTOMapper::fromUsertoDTO)
//                .toList();
//        return new ResponseEntity<Collection<UserDTO>>(usersDTO, HttpStatus.OK);
//    }

    @GetMapping(value = "/userEmail/{userEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable("userEmail") String userEmail) {
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