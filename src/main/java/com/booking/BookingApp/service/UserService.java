package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.*;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.repository.AccommodationRepository;
import com.booking.BookingApp.repository.HostRepository;
import com.booking.BookingApp.repository.RequestRepository;
import com.booking.BookingApp.repository.UserRepository;
import com.booking.BookingApp.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    HostRepository hostRepository;

    @Autowired
    AccommodationRepository accommodationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Override
    public Collection<User> findAll() {
        return data();
    }

    @Override
    public User findOne(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Collection<User> findByStatus(Status userStatus) {
        return userRepository.findByAccount_Status(userStatus);
    }

    @Override
    public User findByUsername(String username){
        return userRepository.findByAccount_Username(username);
    }
  
    @Override
    public boolean activateUser(Long id){
        User user = userRepository.findById(id).orElse(null);
        if(user != null) {
            user.getAccount().setStatus(Status.ACTIVE);
            return true;
        }
        return false;
    }

    @Override
    public User update(User updatedUser) throws Exception {
        System.out.println(updatedUser);
        Long userId = updatedUser.getId();
        User existingUser = userRepository.findById(userId).orElse(null);

        if (existingUser == null) {
            throw new Exception("User not found with ID: " + userId);
        }

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setAccount(updatedUser.getAccount());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setPicturePath(updatedUser.getPicturePath());

        System.out.println(existingUser);

        return userRepository.save(existingUser);
    }


    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user.getAccount().getRoles().get(0).getName().equals("ROLE_GUEST")) {
            deleteGuest(user);
        } else if (user.getAccount().getRoles().get(0).getName().equals("ROLE_HOST")) {
            deleteHost(user);
        }
    }

    @Override
    public void deleteGuest(User user) {
        Guest guest = new Guest(user.getId(), user.getFirstName(), user.getLastName(), user.getAddress(),
                user.getPhoneNumber(), user.getAccount(), user.getPicturePath(),
                userRepository.findFavoriteAccommodationsByGuestId(user.getId()));
        Collection<Request> reservations = requestRepository.findActiveReservationsForGuest(LocalDateTime.now(), guest);
        if(reservations.isEmpty()) {
            Collection<Request> requests = requestRepository.findByGuest_Id(guest.getId());
            if(!requests.isEmpty()){
                requestRepository.deleteAll(requests);
            }
            userRepository.deleteById(user.getId());
            System.out.println(userRepository.findAll());
        }
    }

    @Override
    public void deleteHost(User user) {
        Host host = new Host(user.getId(), user.getFirstName(), user.getLastName(),
                user.getAddress(), user.getPhoneNumber(), user.getAccount(), user.getPicturePath());
        Collection<Request> reservations = requestRepository.findActiveReservationsForHost(LocalDate.now(), host);
        if (!reservations.isEmpty()) {
            Collection<Accommodation> accommodations = accommodationRepository.findAllByHost(host);
            if(!accommodations.isEmpty()){
                for(Accommodation a: accommodations){
                    userRepository.deleteFavoriteAccommodationsByAccommodationId(a.getId());
                }
                accommodationRepository.deleteAll(accommodations);
            }
            Collection<Request> requests = requestRepository.findByAccommodation_Host(host);
            if (!reservations.isEmpty()) {
                requestRepository.deleteAll(requests);
            }
            hostRepository.deleteHostById(user.getId());
            hostRepository.deleteUserById(user.getId());
        }
    }

    @Override
    public Collection<Accommodation> findFavorites(Long id) {
        return userRepository.findFavoriteAccommodationsByGuestId(id);
    }

    @Override
    public User save(User userRequest) {
        User u = new User();
        u.setUsername(userRequest.getUsername());

        // pre nego sto postavimo lozinku u atribut hesiramo je kako bi se u bazi nalazila hesirana lozinka
        // treba voditi racuna da se koristi isi password encoder bean koji je postavljen u AUthenticationManager-u kako bi koristili isti algoritam
        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        u.setFirstName(userRequest.getFirstName());
        u.setLastName(userRequest.getLastName());
        u.setPhoneNumber(userRequest.getPhoneNumber());
        u.setAddress(userRequest.getAddress());
        u.setAccount(userRequest.getAccount());
        u.setPicturePath(userRequest.getPicturePath());

        // u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
        List<Role> roles = roleService.findByName(userRequest.getAccount().getRoles().get(0).getName());
        u.getAccount().setRoles(roles);

        return this.userRepository.save(u);
    }


    public User dataOne(){
        Role role=new Role(1L,"guest");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        Address address = new Address("Srbija","Novi Sad","21000","Futoska 1");
        Account account = new Account(1L, "isidorica","slatkica",Status.ACTIVE, roles);
        return new User(1L,"Isidora","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg");
    }
    public List<User> data() {
        List<User> users = new ArrayList<>();
        Address address = new Address("Srbija","Novi Sad","21000","Futoska 1");
        Role role=new Role(1L,"guest");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        Account account = new Account(1L, "aleksicisidora@yahoo.com","682002",Status.ACTIVE, roles);
        users.add(new User(1L,"Isidora","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg"));
        users.add(new User(2L,"Tamara","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg"));
        users.add(new User(3L,"Kikica","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg"));
        users.add(new User(4L,"Kile","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg"));
        users.add(new User(5L,"Isidorica","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg"));
        users.add(new User(6L,"Isi","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg"));
        users.add(new User(7L,"Kika","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg"));
        users.add(new User(8L,"Tamarica","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg"));
        users.add(new User(9L,"Taki","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg"));
        return users;
    }
}

