package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.dto.TimeSlotDTO;
import com.booking.BookingApp.repository.AccommodationRepository;
import com.booking.BookingApp.repository.RequestRepository;
import com.booking.BookingApp.repository.RequestRepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest()
@TestPropertySource("classpath:application-test.properties")
public class RequestServiceTest {

    @Mock
    RequestRepository requestRepository;

    @Mock
    AccommodationService accommodationService;

    @Mock
    AccommodationRepository accommodationRepository;

    @Mock
    AvailabilityService availabilityService;

    @InjectMocks
    RequestService requestService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createRequest_PastDate() throws Exception {
        TimeSlot timeSlot=getDateInPast();
        Accommodation accommodation=new Accommodation();
        accommodation.setMinGuests(1);
        accommodation.setMaxGuests(5);
        Request request = new Request(1L, timeSlot, 200, null, accommodation, 3, RequestStatus.PENDING, false);
        Request result=requestService.create(request);
        assertNull(result);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(availabilityService);
        verifyNoInteractions(requestRepository);
    }
    @Test
    public void createRequest_EndBeforeStartDate() throws Exception {
        TimeSlot timeSlot=getEndBeforeStartDate();
        Accommodation accommodation=new Accommodation();
        Request request = new Request(1L, timeSlot, 200, null, accommodation, 3, RequestStatus.PENDING, false);
        Request result=requestService.create(request);
        assertNull(result);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(availabilityService);
        verifyNoInteractions(requestRepository);
    }
    @Test
    public void createRequest_InvalidDates() throws Exception {
        TimeSlot timeSlot=getInvalidDates();
        Accommodation accommodation=new Accommodation();
        Collection<TimeSlot>freeTimeSlots=new ArrayList<>();
        freeTimeSlots.add(getValidDates());
        accommodation.setFreeTimeSlots(freeTimeSlots);
        when(availabilityService.checkFreeTimeSlots(timeSlot,accommodation)).thenReturn(false);

        Request request = new Request(1L, timeSlot, 200, null, accommodation, 3, RequestStatus.PENDING, false);
        Request result=requestService.create(request);
        assertNull(result);
        verify(availabilityService).checkFreeTimeSlots(timeSlot,accommodation);
        verifyNoMoreInteractions(availabilityService);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(requestRepository);
    }
    @ParameterizedTest
    @CsvSource(value ={
            "1",
            "10"
    })
    public void createRequest_InvalidGuestNumber(int guestNum) throws Exception {
        TimeSlot timeSlot=getValidDates();

        Accommodation accommodation=new Accommodation();
        Collection<TimeSlot>freeTimeSlots=new ArrayList<>();
        freeTimeSlots.add(getValidDates());
        accommodation.setFreeTimeSlots(freeTimeSlots);
        accommodation.setMinGuests(2);
        accommodation.setMaxGuests(5);

        when(availabilityService.checkFreeTimeSlots(timeSlot,accommodation)).thenReturn(true);

        Request request = new Request(1L, timeSlot, 200, null, accommodation, guestNum, RequestStatus.PENDING, false);
        Request result=requestService.create(request);
        assertNull(result);
        verify(availabilityService).checkFreeTimeSlots(timeSlot,accommodation);
        verifyNoMoreInteractions(availabilityService);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(requestRepository);
    }
    @ParameterizedTest
    @CsvSource(value ={
            "2",
            "5",
            "3"
    })
    public void createRequest_PriceNotDefined(int guestNum) throws Exception {
        TimeSlot timeSlot=getValidDates();
        Date startDate=Date.from(timeSlot.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate=Date.from(timeSlot.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant());

        Accommodation accommodation=new Accommodation();
        accommodation.setId(1L);
        Collection<TimeSlot>freeTimeSlots=new ArrayList<>();
        freeTimeSlots.add(getValidDates());
        accommodation.setFreeTimeSlots(freeTimeSlots);
        accommodation.setMinGuests(2);
        accommodation.setMaxGuests(5);

        when(availabilityService.checkFreeTimeSlots(timeSlot,accommodation)).thenReturn(true);
        when(accommodationService.calculatePriceForAccommodation(1L,guestNum,startDate,endDate)).thenReturn(0.0);
        Request request = new Request(1L, timeSlot, 0, null, accommodation, guestNum, RequestStatus.PENDING, false);

        Request result=requestService.create(request);
        assertNull(result);

        verify(availabilityService).checkFreeTimeSlots(timeSlot,accommodation);
        verify(accommodationService).calculatePriceForAccommodation(1L,guestNum,startDate,endDate);

        verifyNoMoreInteractions(availabilityService);
        verifyNoMoreInteractions(accommodationService);
        verifyNoInteractions(requestRepository);
    }
    @Test
    public void createRequest_PricesDiffer() throws Exception {
        TimeSlot timeSlot=getValidDates();
        Date startDate=Date.from(timeSlot.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate=Date.from(timeSlot.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant());

        Accommodation accommodation=new Accommodation();
        accommodation.setId(1L);
        Collection<TimeSlot>freeTimeSlots=new ArrayList<>();
        freeTimeSlots.add(getValidDates());
        accommodation.setFreeTimeSlots(freeTimeSlots);
        accommodation.setMinGuests(1);
        accommodation.setMaxGuests(5);

        Request request = new Request(1L, timeSlot, 1000.0, null, accommodation, 3, RequestStatus.PENDING, false);


        when(availabilityService.checkFreeTimeSlots(timeSlot,accommodation)).thenReturn(true);
        when(accommodationService.calculatePriceForAccommodation(1L,3,startDate,endDate)).thenReturn(2000.0);

        Request result=requestService.create(request);
        assertNull(result);

        verify(availabilityService).checkFreeTimeSlots(timeSlot,accommodation);
        verify(accommodationService,times(2)).calculatePriceForAccommodation(1L,3,startDate,endDate);

        verifyNoMoreInteractions(availabilityService);
        verifyNoMoreInteractions(accommodationService);
        verifyNoInteractions(requestRepository);
    }

    @Test
    public void createRequest_Valid() throws Exception {
        TimeSlot timeSlot=getValidDates();
        Date startDate=Date.from(timeSlot.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate=Date.from(timeSlot.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant());

        Accommodation accommodation=new Accommodation();
        accommodation.setId(1L);
        Collection<TimeSlot>freeTimeSlots=new ArrayList<>();
        freeTimeSlots.add(getValidDates());
        accommodation.setFreeTimeSlots(freeTimeSlots);
        accommodation.setMinGuests(1);
        accommodation.setMaxGuests(5);

        Request request = new Request(1L, timeSlot, 2000.0, null, accommodation, 3, RequestStatus.PENDING, false);

        when(availabilityService.checkFreeTimeSlots(timeSlot,accommodation)).thenReturn(true);
        when(accommodationService.calculatePriceForAccommodation(1L,3,startDate,endDate)).thenReturn(2000.0);
        when(requestRepository.save(request)).thenReturn(request);

        Request result=requestService.create(request);
        assertNotNull(result);
        assertEquals(result,request);

        verify(availabilityService).checkFreeTimeSlots(timeSlot,accommodation);
        verify(accommodationService,times(2)).calculatePriceForAccommodation(1L,3,startDate,endDate);
        verify(requestRepository).save(request);

        verifyNoMoreInteractions(availabilityService);
        verifyNoMoreInteractions(accommodationService);
        verifyNoMoreInteractions(requestRepository);
    }


    private TimeSlot getInvalidDates() { //not in the free time slots
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusDays(20);
        LocalDate endDate = now.plusDays(30);

        return new TimeSlot(startDate, endDate);
    }
    private TimeSlot getValidDates() {  //free time slots will be this
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusDays(5);
        LocalDate endDate = now.plusDays(10);

        return new TimeSlot(startDate, endDate);
    }
    private TimeSlot getDateInPast() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusDays(7);
        LocalDate endDate = now.plusDays(3);

        return new TimeSlot(startDate, endDate);
    }
    private TimeSlot getEndBeforeStartDate() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusDays(5);
        LocalDate endDate = now.plusDays(1);

        return new TimeSlot(startDate, endDate);
    }

//    ovde pisem testove za rucnu potvrdu


    //testiramo accept kad imamo samo jednu rezervaciju koju prihvatamo
    @Test
    void acceptWhereThereIsNoOverlappingReservationRequestsTest() {
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setFreeTimeSlots(getAccommodationFreeTimeSlot());

        Request request = new Request();
        request.setId(1L);
        request.setAccommodation(accommodation);
        request.setStatus(RequestStatus.PENDING);
        request.setTimeSlot(getReservationTimeSlot());

        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(request.getTimeSlot().getStartDate()
                ,request.getTimeSlot().getEndDate());

        Accommodation expectedAccommodation = new Accommodation();
        expectedAccommodation.setId(1L);
        expectedAccommodation.setFreeTimeSlots(getNewFreeTimeSlots());

        when(requestRepository.findAllByAccommodationAndTimeSlot(accommodation,
                request.getTimeSlot().getStartDate(), request.getTimeSlot().getEndDate()))
                .thenReturn(Collections.emptyList());


        when(accommodationService.changeFreeTimeSlotsAcceptingReservation(1L,timeSlotDTO))
                .thenReturn(expectedAccommodation);

        when(requestRepository.save(request)).thenReturn(request);

        Request result = requestService.accept(request);

        assertEquals(RequestStatus.ACCEPTED, result.getStatus());
        verify(requestRepository).findAllByAccommodationAndTimeSlot(accommodation,
                request.getTimeSlot().getStartDate(), request.getTimeSlot().getEndDate());
        verify(requestRepository, times(1)).save(request);
        verify(accommodationService).changeFreeTimeSlotsAcceptingReservation(eq(1L), any(TimeSlotDTO.class));

    }


    //testiramo accept kad zahtevi za rezervaciju nisu napravljeni za isti smestaj
    @Test
    void acceptWhereReservationRequestsAreNotForTheSameAccommodation() {
        Accommodation accommodation1 = new Accommodation();
        accommodation1.setId(1L);
        accommodation1.setFreeTimeSlots(getAccommodationFreeTimeSlot());

        Accommodation accommodation2 = new Accommodation();
        accommodation2.setId(2L);
        accommodation2.setFreeTimeSlots(getAccommodationFreeTimeSlot());

        Request request1 = new Request();
        request1.setId(1L);
        request1.setAccommodation(accommodation1);
        request1.setStatus(RequestStatus.PENDING);
        request1.setTimeSlot(getReservationTimeSlot());

        Request request2 = new Request();
        request2.setId(1L);
        request2.setAccommodation(accommodation2);
        request2.setStatus(RequestStatus.PENDING);
        request2.setTimeSlot(getReservationTimeSlot());

        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(request1.getTimeSlot().getStartDate()
                ,request1.getTimeSlot().getEndDate());

        Accommodation expectedAccommodation = new Accommodation();
        expectedAccommodation.setId(1L);
        expectedAccommodation.setFreeTimeSlots(getNewFreeTimeSlots());

        when(requestRepository.findAllByAccommodationAndTimeSlot(accommodation1,
                request1.getTimeSlot().getStartDate(), request1.getTimeSlot().getEndDate()))
                .thenReturn(Collections.emptyList());


        when(accommodationService.changeFreeTimeSlotsAcceptingReservation(1L,timeSlotDTO))
                .thenReturn(expectedAccommodation);

        when(requestRepository.save(request1)).thenReturn(request1);

        Request result = requestService.accept(request1);

        assertEquals(RequestStatus.ACCEPTED, result.getStatus());
        assertEquals(RequestStatus.PENDING, request2.getStatus());

        verify(requestRepository).findAllByAccommodationAndTimeSlot(accommodation1,
                request1.getTimeSlot().getStartDate(), request1.getTimeSlot().getEndDate());
        verify(requestRepository, times(1)).save(request1);
        verify(accommodationService).changeFreeTimeSlotsAcceptingReservation(eq(1L), any(TimeSlotDTO.class));

    }

    //testiramo accept kad imamo rezervacije koje se preklapaju
    @Test
    void acceptWhereThereIsOverlappingReservationRequestsTest() {
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setFreeTimeSlots(getAccommodationFreeTimeSlot());

        LocalDate now =LocalDate.now();
        TimeSlot timeSlot1 = new TimeSlot(now.plusDays(5),now.plusDays(7));
        TimeSlot timeSlot2 = new TimeSlot(now.plusDays(6),now.plusDays(8));
        TimeSlot timeSlot3 = new TimeSlot(now.plusDays(3),now.plusDays(5));
        TimeSlot timeSlot4 = new TimeSlot(now.plusDays(4),now.plusDays(6));
        TimeSlot timeSlot5 = new TimeSlot(now.plusDays(7),now.plusDays(9));

        Request request1 = new Request();
        request1.setId(1L);
        request1.setAccommodation(accommodation);
        request1.setStatus(RequestStatus.PENDING);
        request1.setTimeSlot(timeSlot1);

        Request request2 = new Request();
        request2.setId(2L);
        request2.setAccommodation(accommodation);
        request2.setStatus(RequestStatus.PENDING);
        request2.setTimeSlot(timeSlot2);

        Request request3 = new Request();
        request3.setId(3L);
        request3.setAccommodation(accommodation);
        request3.setStatus(RequestStatus.PENDING);
        request3.setTimeSlot(timeSlot3);

        Request request4 = new Request();
        request4.setId(4L);
        request4.setAccommodation(accommodation);
        request4.setStatus(RequestStatus.PENDING);
        request4.setTimeSlot(timeSlot4);


        Request request5 = new Request();
        request5.setId(5L);
        request5.setAccommodation(accommodation);
        request5.setStatus(RequestStatus.PENDING);
        request5.setTimeSlot(timeSlot5);

        Request request6 = new Request();
        request6.setId(5L);
        request6.setAccommodation(accommodation);
        request6.setStatus(RequestStatus.PENDING);
        request6.setTimeSlot(timeSlot1);


        TimeSlotDTO timeSlotDTO1 = new TimeSlotDTO(request1.getTimeSlot().getStartDate()
                ,request1.getTimeSlot().getEndDate());

        Accommodation expectedAccommodation = new Accommodation();
        expectedAccommodation.setId(1L);
        expectedAccommodation.setFreeTimeSlots(getFreeTimeSlotsAfterAccepting());

        Collection<Request> requestsThatWillBeDeclined = new ArrayList<>();
        requestsThatWillBeDeclined.add(request2);
        requestsThatWillBeDeclined.add(request3);
        requestsThatWillBeDeclined.add(request4);
        requestsThatWillBeDeclined.add(request5);
        requestsThatWillBeDeclined.add(request6);

        when(requestRepository.findAllByAccommodationAndTimeSlot(accommodation,
                request1.getTimeSlot().getStartDate(), request1.getTimeSlot().getEndDate()))
                .thenReturn(requestsThatWillBeDeclined);
        when(accommodationService.changeFreeTimeSlotsAcceptingReservation
                (1L,timeSlotDTO1)).thenReturn(expectedAccommodation);
        when(requestRepository.save(request1)).thenReturn(request1);


        Request result = requestService.accept(request1);


        assertEquals(RequestStatus.ACCEPTED, result.getStatus());
        for (Request declinedRequest : requestsThatWillBeDeclined) {
            assertEquals(RequestStatus.DENIED, declinedRequest.getStatus());
        }

        verify(requestRepository).findAllByAccommodationAndTimeSlot(accommodation,
                request1.getTimeSlot().getStartDate(), request1.getTimeSlot().getEndDate());
        verify(requestRepository, times(1)).save(request1);
        verify(requestRepository, times(6)).save(any(Request.class));
        verify(accommodationService).changeFreeTimeSlotsAcceptingReservation(eq(1L), any(TimeSlotDTO.class));

    }

    //tetiramo deny kad imamo samo jednu rezervaciju da odbijemo
    @Test
    void denyTest() {
        Request request = new Request();
        request.setId(1L);
        request.setStatus(RequestStatus.PENDING);
        when(requestRepository.save(request)).thenReturn(request);
        Request result = requestService.deny(request);
        assertEquals(RequestStatus.DENIED, result.getStatus());
        verify(requestRepository, times(1)).save(request);
    }




    private TimeSlot getReservationTimeSlot() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusDays(5);
        LocalDate endDate = now.plusDays(10);
        return new TimeSlot(startDate, endDate);
    }

    private ArrayList<TimeSlot> getAccommodationFreeTimeSlot() {
        ArrayList<TimeSlot> freeTimeSlots = new ArrayList<>();

        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusDays(1);
        LocalDate endDate = now.plusDays(15);

        freeTimeSlots.add( new TimeSlot(startDate, endDate));
        return freeTimeSlots;
    }

    private ArrayList<TimeSlot> getNewFreeTimeSlots() {
        ArrayList<TimeSlot> freeTimeSlots = new ArrayList<>();

        LocalDate now = LocalDate.now();
        LocalDate startDate1 = now.plusDays(1);
        LocalDate endDate1 = now.plusDays(4);

        LocalDate startDate2 = now.plusDays(11);
        LocalDate endDate2 = now.plusDays(15);

        freeTimeSlots.add( new TimeSlot(startDate1, endDate1));
        freeTimeSlots.add( new TimeSlot(startDate2, endDate2));

        return freeTimeSlots;
    }

    private ArrayList<TimeSlot> getFreeTimeSlotsAfterAccepting() {
        ArrayList<TimeSlot> freeTimeSlots = new ArrayList<>();

        LocalDate now = LocalDate.now();
        LocalDate startDate1 = now.plusDays(1);
        LocalDate endDate1 = now.plusDays(4);

        LocalDate startDate2 = now.plusDays(8);
        LocalDate endDate2 = now.plusDays(15);

        freeTimeSlots.add( new TimeSlot(startDate1, endDate1));
        freeTimeSlots.add( new TimeSlot(startDate2, endDate2));

        return freeTimeSlots;
    }
}
