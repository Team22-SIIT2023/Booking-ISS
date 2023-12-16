package com.booking.BookingApp.domain;

import com.booking.BookingApp.domain.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "host_comments")
//@SQLDelete(sql = "UPDATE host_comments SET deleted = true WHERE id=?")
//@Where(clause = "deleted = false")
public class HostComments extends Comments{

    @Column(name = "host_id")
    private Long hostId;
//
//    @Column(name="deleted")
//    private boolean deleted = Boolean.FALSE;

    public HostComments(Long id, String text, LocalDate date, double rating, Status status, Guest guest, Long hostId, boolean deleted) {
        super(id, text, date, rating, status, guest, deleted);
        this.hostId = hostId;
    }
}
