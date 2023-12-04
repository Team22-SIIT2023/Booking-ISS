package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.User;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.dto.UserDTO;

import java.util.Collection;

public interface IUserService {

    Collection<User> findAll();

    User findOne(Long id);

    User findLoggedUser(String username, String password);

    Collection<User> findAllByStatus(Status userStatus);

    User findOneByEmail(String email);

    boolean activateUser(Long id);

    User create(User user) throws Exception;

    User update(User user) throws Exception;

    void delete(Long id);


}
