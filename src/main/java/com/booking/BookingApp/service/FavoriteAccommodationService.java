package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.FavoriteAccommodation;
import com.booking.BookingApp.dto.FavoriteAccommodationDTO;
import com.booking.BookingApp.service.interfaces.IFavoriteAccommodationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class FavoriteAccommodationService implements IFavoriteAccommodationService {
    @Override
    public FavoriteAccommodationDTO findOne(Long id) {
        return new FavoriteAccommodationDTO(1L, 101L, 1L);
    }

    @Override
    public Collection<FavoriteAccommodationDTO> findAllForGuest(Long id) {
        return data();
    }

    @Override
    public FavoriteAccommodationDTO create(FavoriteAccommodationDTO favoriteAccommodation) throws Exception {
        return null;
    }

    @Override
    public void delete(Long id) {}

    public Collection<FavoriteAccommodationDTO> data() {
        Collection<FavoriteAccommodationDTO> favoritesList = new ArrayList<>();

        // Add instances of FavoriteAccommodationDTO to the list
        favoritesList.add(new FavoriteAccommodationDTO(1L, 101L, 1L));
        favoritesList.add(new FavoriteAccommodationDTO(2L, 102L, 2L));
        favoritesList.add(new FavoriteAccommodationDTO(3L, 103L, 3L));

        return favoritesList;
    }
}
