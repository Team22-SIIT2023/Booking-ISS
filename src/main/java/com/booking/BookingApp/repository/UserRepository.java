package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.Account;
import com.booking.BookingApp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
