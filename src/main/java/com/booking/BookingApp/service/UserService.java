package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.dto.UserDTO;
import com.booking.BookingApp.service.interfaces.IUserService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService implements IUserService {

    @Override
    public Collection<UserDTO> findAll() {
        return null;
    }

    @Override
    public UserDTO findOne(Long id) {
        return null;
    }

    @Override
    public Collection<UserDTO> findAllByStatus(Status userStatus) {
        return null;
    }

    @Override
    public UserDTO findOneByEmail(String email) {
        return null;
    }

    @Override
    public UserDTO create(UserDTO user) throws Exception {
        return null;
    }

    @Override
    public UserDTO update(UserDTO user) throws Exception {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
