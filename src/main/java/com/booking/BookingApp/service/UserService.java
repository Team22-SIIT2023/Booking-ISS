package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Account;
import com.booking.BookingApp.domain.Address;
import com.booking.BookingApp.domain.User;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.domain.enums.UserType;
import com.booking.BookingApp.dto.AccountDTO;
import com.booking.BookingApp.dto.AddressDTO;
import com.booking.BookingApp.dto.ReportDTO;
import com.booking.BookingApp.dto.UserDTO;
import com.booking.BookingApp.repository.UserRepository;
import com.booking.BookingApp.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public Collection<User> findAll() {
        return data();
    }

    @Override
    public User findOne(Long id) {
        Address address = new Address("Srbija","Novi Sad","21000","Futoska 1");
        Account account = new Account(1L, "isidorica","slatkica",Status.ACTIVE, UserType.GUEST);
        return new User(1L,"Isidora","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg");
    }

    @Override
    public User findLoggedUser(String username, String password) {
        Address address = new Address("Srbija","Novi Sad","21000","Futoska 1");
        Account account = new Account(1L, "isidorica","slatkica",Status.ACTIVE, UserType.GUEST);
        return new User(1L,"Isidora","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg");
    }

    @Override
    public Collection<User> findAllByStatus(Status userStatus) {
        return data();
    }

    @Override
    public User findOneByEmail(String email) {
        Address address = new Address("Srbija","Novi Sad","21000","Futoska 1");
        Account account = new Account(1L, "isidorica","slatkica",Status.ACTIVE, UserType.GUEST);
        return new User(1L,"Isidora","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg");
    }

    @Override
    public boolean activateUser(Long id){
        return true;
    }
    @Override
    public User create(User user) throws Exception {
        Address address = new Address("Srbija","Novi Sad","21000","Futoska 1");
        Account account = new Account(1L, "isidorica","slatkica",Status.ACTIVE, UserType.GUEST);
        return new User(1L,"Isidora","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg");
    }

    @Override
    public User update(User user) throws Exception {
        Address address = new Address("Srbija","Novi Sad","21000","Futoska 1");
        Account account = new Account(1L, "isidorica","slatkica",Status.ACTIVE, UserType.GUEST);
        return new User(1L,"Isidora","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg");
    }

    @Override
    public void delete(Long id) {}

    @Override
    public Collection<Accommodation> findFavorites(Long id) {
        return userRepository.findFavoriteAccommodationsByGuestId(id);
    }


    public List<User> data() {
        List<User> users = new ArrayList<>();
        Address address = new Address("Srbija","Novi Sad","21000","Futoska 1");
        Account account = new Account(1L, "isidorica","slatkica",Status.ACTIVE, UserType.GUEST);
        users.add(new User(1L,"Isidora","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg"));
        users.add(new User(2L,"Tamara","Aleksic",address,"0692104221",account,"../../../assets/images/userpicture.jpg"));
        return users;
    }
}

