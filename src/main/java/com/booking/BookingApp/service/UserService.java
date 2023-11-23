package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.domain.enums.UserType;
import com.booking.BookingApp.dto.AccountDTO;
import com.booking.BookingApp.dto.AddressDTO;
import com.booking.BookingApp.dto.ReportDTO;
import com.booking.BookingApp.dto.UserDTO;
import com.booking.BookingApp.service.interfaces.IUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Override
    public Collection<UserDTO> findAll() {
        return data();
    }

    @Override
    public UserDTO findOne(Long id) {
        AddressDTO addressDTO = new AddressDTO("Srbija","Novi Sad","Futoska 1");
        AccountDTO accountDTO = new AccountDTO("isidorica",Status.ACTIVE, UserType.GUEST);
        return new UserDTO(1L,"Isidora","Aleksic",addressDTO,"0692104221",accountDTO);
    }

    @Override
    public Collection<UserDTO> findAllByStatus(Status userStatus) {
        return data();
    }

    @Override
    public UserDTO findOneByEmail(String email) {
        AddressDTO addressDTO = new AddressDTO("Srbija","Novi Sad","Futoska 1");
        AccountDTO accountDTO = new AccountDTO("isidorica",Status.ACTIVE, UserType.GUEST);
        return new UserDTO(1L,"Isidora","Aleksic",addressDTO,"0692104221",accountDTO);
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


    public List<UserDTO> data() {
        List<UserDTO> userDTOS = new ArrayList<>();

        AddressDTO addressDTO = new AddressDTO("Srbija","Novi Sad","Futoska 1");
        AccountDTO accountDTO = new AccountDTO("isidorica",Status.ACTIVE, UserType.GUEST);
        // Add instances of AccommodationDTO to the list
        userDTOS.add(new UserDTO(1L,"Isidora","Aleksic",addressDTO,"0692104221",accountDTO));
        userDTOS.add(new UserDTO(2L,"Tamara","Aleksic",addressDTO,"0692104221",accountDTO));
        return userDTOS;
    }
}

