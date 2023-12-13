package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.User;
import com.booking.BookingApp.domain.enums.Status;

import java.util.Collection;
import java.util.Optional;

public interface IUserService {

    Collection<User> findAll();
    User findByUsername(String username);

    User findOne(Long id);

    Collection<User> findByStatus(Status userStatus);

    boolean activateUser(Long id);

    User update(User user) throws Exception;

    void delete(Long id);

    void deleteHost(User user);

    void deleteGuest(User user);

    Collection<Accommodation> findFavorites(Long id);

    User save(User user);
}
