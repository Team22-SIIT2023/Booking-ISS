package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.FavoriteAccommodation;
import com.booking.BookingApp.domain.Notification;
import com.booking.BookingApp.dto.FavoriteAccommodationDTO;

import java.util.Collection;

public interface IFavoriteAccommodationService {
    FavoriteAccommodationDTO findOne(Long id);
    Collection<FavoriteAccommodationDTO> findAllForGuest(Long id);
    FavoriteAccommodationDTO create(FavoriteAccommodationDTO favoriteAccommodation) throws Exception;
    void delete(Long id);
}
