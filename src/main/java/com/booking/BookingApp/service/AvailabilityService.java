package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.service.interfaces.IAvailabilityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AvailabilityService implements IAvailabilityService {

    @Override
    public boolean checkFreeTimeSlots(TimeSlot newTimeSlot, Accommodation accommodation) {
        Collection<TimeSlot>freeTimeSlots=accommodation.getFreeTimeSlots();
        for(TimeSlot timeSlot:freeTimeSlots){
            if((timeSlot.getStartDate().isBefore(newTimeSlot.getStartDate()) || timeSlot.getStartDate().equals(newTimeSlot.getStartDate()) ) &&
                    (timeSlot.getEndDate().isAfter(newTimeSlot.getEndDate()) || timeSlot.getEndDate().equals(newTimeSlot.getEndDate())) ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<TimeSlot> updateFreeTimeSlots(Accommodation accommodation) {
        return null;
    }

}
