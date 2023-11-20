package com.booking.BookingApp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FavoriteAccommodation {
    private Long id;
    private Long guestId;
    private Long accommodationId;

    public FavoriteAccommodation(Long id, Long guestId, Long accommodationId) {
        this.id = id;
        this.guestId = guestId;
        this.accommodationId = accommodationId;
    }

    @Override
    public String toString() {
        return "FavoriteAccommodation{" +
                "id=" + id +
                ", guestId=" + guestId +
                ", accommodationId=" + accommodationId +
                '}';
    }
}
