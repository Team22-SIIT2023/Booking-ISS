package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.FavoriteAccommodation;
import com.booking.BookingApp.service.interfaces.IFavoriteAccommodationService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FavoriteAccommodationService implements IFavoriteAccommodationService {
    @Override
    public FavoriteAccommodation findOne(Long id) {
        return null;
    }

    @Override
    public Collection<FavoriteAccommodation> findAllForGuest(Long id) {
        return null;
    }

    @Override
    public FavoriteAccommodation create(FavoriteAccommodation favoriteAccommodation) throws Exception {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
