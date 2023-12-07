package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Account;
import com.booking.BookingApp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT g.favoriteAccommodations FROM User u JOIN Guest g ON u.id = g.id WHERE u.id = :guestId")
    Collection<Accommodation> findFavoriteAccommodationsByGuestId(Long guestId);

}
