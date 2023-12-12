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
                    "WHERE a.address.country = :country AND " +
                    "a.type = :accommodationType AND " +
                    "a.maxGuests >= :guestNumber AND " +
                    "a.minGuests <= :guestNumber AND " +
                    "(:startDate IS NULL OR :endDate IS NULL OR " +
                    "(fts.startDate <= TO_DATE(:endDate, 'yyyy-MM-dd') AND fts.endDate >= TO_DATE(:startDate, 'yyyy-MM-dd')))")
    Collection<Accommodation> findAccommodationsByCountryTypeGuestNumberAndTimeRange(
            @Param("country") String country,
            @Param("accommodationType") AccommodationType accommodationType,
            @Param("guestNumber") int guestNumber,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate);

//    @Query(
//            "SELECT DISTINCT a FROM Accommodation a " +
//                    "JOIN a.freeTimeSlots fts " +
//                    "LEFT JOIN a.amenities amen " + // Add LEFT JOIN for amenities
//                    "WHERE a.address.country = :country AND " +
//                    "a.type = :accommodationType AND " +
//                    "a.maxGuests >= :guestNumber AND " +
//                    "a.minGuests <= :guestNumber AND " +
//                    "(:startDate IS NULL OR :endDate IS NULL OR " +
//                    "(fts.startDate <= TO_DATE(:endDate, 'yyyy-MM-dd') AND fts.endDate >= TO_DATE(:startDate, 'yyyy-MM-dd'))) AND " +
//                    "(:amenities IS NULL OR amen.name IN :amenities)"
//    )
//    Collection<Accommodation> findAccommodationsByCountryTypeGuestNumberTimeRangeAndAmenities(
//            @Param("country") String country,
//            @Param("accommodationType") AccommodationType accommodationType,
//            @Param("guestNumber") int guestNumber,
//            @Param("startDate") String startDate,
//            @Param("endDate") String endDate,
//            @Param("amenities") List<String> amenities
//    );
@Query(
        "SELECT DISTINCT a FROM Accommodation a " +
                "JOIN a.freeTimeSlots fts " +
                "LEFT JOIN a.amenities amen " +
                "WHERE a.address.country = :country AND " +
                "a.type = :accommodationType AND " +
                "a.maxGuests >= :guestNumber AND " +
                "a.minGuests <= :guestNumber AND " +
                "(:startDate IS NULL OR :endDate IS NULL OR " +
                "(fts.startDate <= TO_DATE(:endDate, 'yyyy-MM-dd') AND fts.endDate >= TO_DATE(:startDate, 'yyyy-MM-dd'))) AND " +
                "(:amenities IS NULL OR " +
                "(SELECT COUNT(DISTINCT amen2.name) FROM Accommodation a2 " +
                "LEFT JOIN a2.amenities amen2 " +
                "WHERE a2.id = a.id AND amen2.name IN :amenities) = :amenitiesCount)"
)
Collection<Accommodation> findAccommodationsByCountryTypeGuestNumberTimeRangeAndAmenities(
        @Param("country") String country,
        @Param("accommodationType") AccommodationType accommodationType,
        @Param("guestNumber") int guestNumber,
        @Param("startDate") String startDate,
        @Param("endDate") String endDate,
        @Param("amenities") List<String> amenities,
        @Param("amenitiesCount") long amenitiesCount
);






}
