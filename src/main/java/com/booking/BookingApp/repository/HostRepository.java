package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HostRepository  extends JpaRepository<Host,Long> {
    @Modifying
    @Query(value = "DELETE FROM users u WHERE u.id = :id",nativeQuery=true)
    void deleteUserById(Long id);

    @Modifying
    @Query(value = "DELETE FROM hosts h WHERE h.id = :id", nativeQuery=true)
    void deleteHostById(Long id);
}
