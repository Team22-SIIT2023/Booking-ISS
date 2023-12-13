package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.domain.enums.AccommodationType;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

@Query(
        "SELECT DISTINCT a FROM Accommodation a " +
                "JOIN a.freeTimeSlots fts " +
                "LEFT JOIN a.amenities amen " +
                "WHERE (:country IS NULL OR a.address.country = :country) AND " +
                "(:city IS NULL OR a.address.city = :city) AND " +
                "(:accommodationType IS NULL OR a.type = :accommodationType) AND " +
                "(:guestNumber IS NULL OR :guestNumber = 0 OR (a.maxGuests >= :guestNumber AND a.minGuests <= :guestNumber)) AND " +
                "(:amenities IS NULL OR " +
                "(SELECT COUNT(DISTINCT amen2.name) FROM Accommodation a2 " +
                "LEFT JOIN a2.amenities amen2 " +
                "WHERE a2.id = a.id AND (:amenities IS NULL OR amen2.name IN :amenities)) = :amenitiesCount) AND " +
                "(EXISTS (" +
                "    SELECT 1 FROM Accommodation a3 " +
                "    JOIN a3.freeTimeSlots fts3 " +
                "    WHERE a3.id = a.id AND " +
                "    TO_DATE(:startDate, 'YYYY-MM-DD') BETWEEN fts3.startDate AND fts3.endDate " +
                "    AND TO_DATE(:endDate, 'YYYY-MM-DD') BETWEEN fts3.startDate AND fts3.endDate" +
                "))"
)
Collection<Accommodation> findAccommodationsByCountryTypeGuestNumberTimeRangeAndAmenities(
        @Param("country") String country,
        @Param("city") String city,
        @Param("accommodationType") AccommodationType accommodationType,
        @Param("guestNumber") Integer guestNumber,
        @Param("startDate") String startDate,
        @Param("endDate") String endDate,
        @Param("amenities") List<String> amenities,
        @Param("amenitiesCount") long amenitiesCount
);

    @Query(
            "SELECT DISTINCT a FROM Accommodation a " +
                    "JOIN a.freeTimeSlots fts " +
                    "LEFT JOIN a.amenities amen " +
                    "WHERE (:country IS NULL OR a.address.country = :country) AND " +
                    "(:city IS NULL OR a.address.city = :city) AND " +
                    "(:accommodationType IS NULL OR a.type = :accommodationType) AND " +
                    "(:guestNumber IS NULL OR :guestNumber = 0 OR (a.maxGuests >= :guestNumber AND a.minGuests <= :guestNumber)) AND " +
                    "(:amenities IS NULL OR " +
                    "(SELECT COUNT(DISTINCT amen2.name) FROM Accommodation a2 " +
                    "LEFT JOIN a2.amenities amen2 " +
                    "WHERE a2.id = a.id AND (:amenities IS NULL OR amen2.name IN :amenities)) = :amenitiesCount)"
    )
    Collection<Accommodation> findAccommodationsByCountryTypeGuestNumberAndAmenities(
            @Param("country") String country,
            @Param("city") String city,
            @Param("accommodationType") AccommodationType accommodationType,
            @Param("guestNumber") Integer guestNumber,
            @Param("amenities") List<String> amenities,
            @Param("amenitiesCount") long amenitiesCount
    );












}
