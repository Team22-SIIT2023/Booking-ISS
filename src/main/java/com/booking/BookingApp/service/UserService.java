package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.*;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.domain.enums.UserType;
import com.booking.BookingApp.dto.AccountDTO;
import com.booking.BookingApp.dto.AddressDTO;
import com.booking.BookingApp.dto.ReportDTO;
import com.booking.BookingApp.dto.UserDTO;
import com.booking.BookingApp.repository.UserRepository;
import com.booking.BookingApp.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

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
        Role role=new Role(1L,"guest");
        Address address = new Address("Srbija","Novi Sad","21000","Futoska 1");
        Account account = new Account(1L, "isidorica","slatkica",Status.ACTIVE, role);
        return new User(1L,"Isidora","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg");
    }

    @Override
    public User findLoggedUser(String username, String password) {
        Address address = new Address("Srbija","Novi Sad","21000","Futoska 1");
        Role role=new Role(1L,"guest");
        Account account = new Account(1L, "isidorica","slatkica",Status.ACTIVE, role);
        return new User(1L,"Isidora","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg");
    }

    @Override
    public Collection<User> findAllByStatus(Status userStatus) {
        return data();
    }

    @Override
    public User findByUsername(String username){
        return userRepository.findByAccount_Username(username);
    }

    @Override
    public User findOneByEmail(String email) {
        Address address = new Address("Srbija","Novi Sad","21000","Futoska 1");
        Role role=new Role(1L,"guest");
        Account account = new Account(1L, "isidorica","slatkica",Status.ACTIVE,role);
        return new User(1L,"Isidora","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg");
    }

    @Override
    public boolean activateUser(Long id){
        return true;
    }
    @Override
    public User create(User user) throws Exception {
        Address address = new Address("Srbija","Novi Sad","21000","Futoska 1");
        Role role=new Role(1L,"guest");
        Account account = new Account(1L, "isidorica","slatkica",Status.ACTIVE, role);
        return new User(1L,"Isidora","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg");
    }

    @Override
    public User update(User user) throws Exception {
        Address address = new Address("Srbija","Novi Sad","21000","Futoska 1");
        Role role=new Role(1L,"guest");
        Account account = new Account(1L, "isidorica","slatkica",Status.ACTIVE, role);
        return new User(1L,"Isidora","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg");
    }

    @Override
    public void delete(Long id) {}

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
        List<Role> roles = roleService.findByName(userRequest.getAccount().getRole().getName());
        u.getAccount().setRole(roles.get(0));

        return this.userRepository.save(u);
    }


    public List<User> data() {
        List<User> users = new ArrayList<>();
        Address address = new Address("Srbija","Novi Sad","21000","Futoska 1");
        Role role=new Role(1L,"guest");
        Account account = new Account(1L, "aleksicisidora@yahoo.com","682002",Status.ACTIVE, role);
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

