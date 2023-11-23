package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.dto.UserDTO;

import java.util.Collection;

public interface IUserService {

    Collection<UserDTO> findAll();

    UserDTO findOne(Long id);

    Collection<UserDTO> findAllByStatus(Status userStatus);

    UserDTO findOneByEmail(String email);

    UserDTO create(UserDTO user) throws Exception;

    UserDTO update(UserDTO user) throws Exception;

    void delete(Long id);


}
