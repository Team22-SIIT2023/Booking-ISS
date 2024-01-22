package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.PricelistItem;
import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.dto.PricelistItemDTO;
import com.booking.BookingApp.dto.TimeSlotDTO;
import com.booking.BookingApp.repository.AccommodationRepository;
import com.booking.BookingApp.repository.TimeSlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class AccommodationServiceTest {

    @Mock
    AccommodationRepository accommodationRepository;

    @Mock
    TimeSlotRepository timeSlotRepository;

    @Mock
    AvailabilityService availabilityService;

    @InjectMocks
    AccommodationService accommodationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_when_reservation_overlaps() throws Exception {
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-03-23"), LocalDate.parse("2024-02-25"));

        Collection<TimeSlot> accommodationFreeTimeslots = getAccommodationFreeTimeslots();

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setFreeTimeSlots(accommodationFreeTimeslots);
        accommodation.setName("Accommodation1");

        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));
        when(availabilityService.reservationOverlaps(timeSlotDTO, 1L)).thenReturn(true);

        Accommodation result = accommodationService.editAccommodationFreeTimeSlots(timeSlotDTO, 1L);

        verify(availabilityService).reservationOverlaps(timeSlotDTO, 1L);
        verifyNoMoreInteractions(availabilityService);
        assertNull(result);
    }

    @Test
    public void test_when_accommodation_id_not_valid() throws Exception {
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-01-25"), LocalDate.parse("2024-01-29"));

        when(accommodationRepository.findById(3L)).thenReturn(Optional.empty());

        Accommodation result = accommodationService.editAccommodationFreeTimeSlots(timeSlotDTO, 3L);

        verifyNoInteractions(availabilityService);
        assertNull(result);
    }

    @Test
    public void test_when_reservation_not_overlaps() throws Exception {
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-01-25"), LocalDate.parse("2024-01-29"));

        Collection<TimeSlot> accommodationFreeTimeslots = getAccommodationFreeTimeslots();

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setFreeTimeSlots(accommodationFreeTimeslots);

        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));
        when(availabilityService.reservationOverlaps(timeSlotDTO, 1L)).thenReturn(false);

        Accommodation result = accommodationService.editAccommodationFreeTimeSlots(timeSlotDTO, 1L);

        verify(availabilityService).reservationOverlaps(timeSlotDTO, 1L);
        verify(availabilityService).updateFreeTimeSlots(timeSlotDTO, accommodationFreeTimeslots);
        assertNotNull(result);
        assertEquals(accommodation.getId(), result.getId());
    }

    public Collection<TimeSlot> getAccommodationFreeTimeslots() {
        TimeSlot accommodationTimeSlot1 = new TimeSlot(LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
        Collection<TimeSlot> accommodationFreeTimeslots = new ArrayList<>();
        accommodationFreeTimeslots.add(accommodationTimeSlot1);
        return  accommodationFreeTimeslots;
    }

    //for edit price

    @Test
    public void test_edit_price_with_invalid_accommodation_id() {
        PricelistItemDTO pricelistItemDTO = new PricelistItemDTO();
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-01-25"), LocalDate.parse("2024-01-29"));
        pricelistItemDTO.setPrice(1000);
        pricelistItemDTO.setTimeSlot(timeSlotDTO);

        when(accommodationRepository.findById(3L)).thenReturn(Optional.empty());

        Accommodation result = accommodationService.editAccommodationPricelistItem(pricelistItemDTO, 3L);

        verify(accommodationRepository).findById(3L);
        verifyNoMoreInteractions(accommodationRepository);
        assertNull(result);
    }

    @Test
    public void test_edit_price_without_overlaps() {
        PricelistItemDTO pricelistItemDTO = new PricelistItemDTO();
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-01-25"), LocalDate.parse("2024-01-29"));
        pricelistItemDTO.setPrice(1000);
        pricelistItemDTO.setTimeSlot(timeSlotDTO);

        Collection<PricelistItem> accommodationPricelist = new ArrayList<>();
        TimeSlot accommodationTimeSlot1 = new TimeSlot(LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
        accommodationPricelist.add(new PricelistItem(accommodationTimeSlot1,800, false));

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setPriceList(accommodationPricelist);

        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        Accommodation result = accommodationService.editAccommodationPricelistItem(pricelistItemDTO, 1L);

        verify(accommodationRepository).findById(1L);
        assertNotNull(result);
        List<PricelistItem> arrayList = result.getPriceList().stream().toList();
        assertEquals(arrayList.get(1).getPrice(), 1000);
        assertEquals(arrayList.get(1).getTimeSlot().getStartDate(), LocalDate.parse("2024-01-25"));
        assertEquals(arrayList.get(1).getTimeSlot().getEndDate(), LocalDate.parse("2024-01-29"));
    }


    @Test
    public void test_edit_price_with_overlaps_new_price_is_between() {
        PricelistItemDTO pricelistItemDTO = new PricelistItemDTO();
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-02-13"), LocalDate.parse("2024-02-17"));
        pricelistItemDTO.setPrice(1000);
        pricelistItemDTO.setTimeSlot(timeSlotDTO);

        Collection<PricelistItem> accommodationPricelist = new ArrayList<>();
        TimeSlot accommodationTimeSlot1 = new TimeSlot(LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
        accommodationPricelist.add(new PricelistItem(accommodationTimeSlot1,800, false));

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setPriceList(accommodationPricelist);

        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        Accommodation result = accommodationService.editAccommodationPricelistItem(pricelistItemDTO, 1L);

        verify(accommodationRepository).findById(1L);
        assertNotNull(result);
        List<PricelistItem> arrayList = result.getPriceList().stream().toList();
        assertEquals(arrayList.get(0).getPrice(), 800);
        assertEquals(arrayList.get(0).getTimeSlot().getStartDate(), LocalDate.parse("2024-02-10"));
        assertEquals(arrayList.get(0).getTimeSlot().getEndDate(), LocalDate.parse("2024-02-13"));

        assertEquals(arrayList.get(1).getPrice(), 1000);
        assertEquals(arrayList.get(1).getTimeSlot().getStartDate(), LocalDate.parse("2024-02-13"));
        assertEquals(arrayList.get(1).getTimeSlot().getEndDate(), LocalDate.parse("2024-02-17"));

        assertEquals(arrayList.get(2).getPrice(), 800);
        assertEquals(arrayList.get(2).getTimeSlot().getStartDate(), LocalDate.parse("2024-02-17"));
        assertEquals(arrayList.get(2).getTimeSlot().getEndDate(), LocalDate.parse("2024-02-20"));
    }


    @Test
    public void test_edit_price_with_overlaps_start() {
        PricelistItemDTO pricelistItemDTO = new PricelistItemDTO();
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-02-03"), LocalDate.parse("2024-02-15"));
        pricelistItemDTO.setPrice(1000);
        pricelistItemDTO.setTimeSlot(timeSlotDTO);

        Collection<PricelistItem> accommodationPricelist = new ArrayList<>();
        TimeSlot accommodationTimeSlot1 = new TimeSlot(LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
        accommodationPricelist.add(new PricelistItem(accommodationTimeSlot1,800, false));

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setPriceList(accommodationPricelist);

        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        Accommodation result = accommodationService.editAccommodationPricelistItem(pricelistItemDTO, 1L);

        verify(accommodationRepository).findById(1L);
        assertNotNull(result);
        List<PricelistItem> arrayList = result.getPriceList().stream().toList();

        assertEquals(arrayList.get(0).getPrice(), 800);
        assertEquals(arrayList.get(0).getTimeSlot().getStartDate(), LocalDate.parse("2024-02-15"));
        assertEquals(arrayList.get(0).getTimeSlot().getEndDate(), LocalDate.parse("2024-02-20"));

        assertEquals(arrayList.get(1).getPrice(), 1000);
        assertEquals(arrayList.get(1).getTimeSlot().getStartDate(), LocalDate.parse("2024-02-03"));
        assertEquals(arrayList.get(1).getTimeSlot().getEndDate(), LocalDate.parse("2024-02-15"));
    }


    @Test
    public void test_edit_price_with_overlaps_end() {
        PricelistItemDTO pricelistItemDTO = new PricelistItemDTO();
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-02-13"), LocalDate.parse("2024-02-25"));
        pricelistItemDTO.setPrice(1000);
        pricelistItemDTO.setTimeSlot(timeSlotDTO);

        Collection<PricelistItem> accommodationPricelist = new ArrayList<>();
        TimeSlot accommodationTimeSlot1 = new TimeSlot(LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
        accommodationPricelist.add(new PricelistItem(accommodationTimeSlot1,800, false));

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setPriceList(accommodationPricelist);

        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        Accommodation result = accommodationService.editAccommodationPricelistItem(pricelistItemDTO, 1L);

        verify(accommodationRepository).findById(1L);
        assertNotNull(result);
        List<PricelistItem> arrayList = result.getPriceList().stream().toList();

        assertEquals(arrayList.get(0).getPrice(), 800);
        assertEquals(arrayList.get(0).getTimeSlot().getStartDate(), LocalDate.parse("2024-02-10"));
        assertEquals(arrayList.get(0).getTimeSlot().getEndDate(), LocalDate.parse("2024-02-13"));

        assertEquals(arrayList.get(1).getPrice(), 1000);
        assertEquals(arrayList.get(1).getTimeSlot().getStartDate(), LocalDate.parse("2024-02-13"));
        assertEquals(arrayList.get(1).getTimeSlot().getEndDate(), LocalDate.parse("2024-02-25"));
    }


    @Test
    public void test_edit_price_with_overlaps() {
        PricelistItemDTO pricelistItemDTO = new PricelistItemDTO();
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-02-03"), LocalDate.parse("2024-02-25"));
        pricelistItemDTO.setPrice(1000);
        pricelistItemDTO.setTimeSlot(timeSlotDTO);

        Collection<PricelistItem> accommodationPricelist = new ArrayList<>();
        TimeSlot accommodationTimeSlot1 = new TimeSlot(LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
        accommodationPricelist.add(new PricelistItem(accommodationTimeSlot1,800, false));

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setPriceList(accommodationPricelist);

        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        Accommodation result = accommodationService.editAccommodationPricelistItem(pricelistItemDTO, 1L);

        verify(accommodationRepository).findById(1L);
        assertNotNull(result);
        List<PricelistItem> arrayList = result.getPriceList().stream().toList();

        assertEquals(arrayList.get(0).getPrice(), 1000);
        assertEquals(arrayList.get(0).getTimeSlot().getStartDate(), LocalDate.parse("2024-02-03"));
        assertEquals(arrayList.get(0).getTimeSlot().getEndDate(), LocalDate.parse("2024-02-25"));
    }

    @Test
    public void test_edit_price_with_overlaps_and_equals_date() {
        PricelistItemDTO pricelistItemDTO = new PricelistItemDTO();
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"));
        pricelistItemDTO.setPrice(1000);
        pricelistItemDTO.setTimeSlot(timeSlotDTO);

        Collection<PricelistItem> accommodationPricelist = new ArrayList<>();
        TimeSlot accommodationTimeSlot1 = new TimeSlot(LocalDate.parse("2024-02-10"), LocalDate.parse("2024-02-20"), false);
        accommodationPricelist.add(new PricelistItem(accommodationTimeSlot1,800, false));

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setPriceList(accommodationPricelist);

        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        Accommodation result = accommodationService.editAccommodationPricelistItem(pricelistItemDTO, 1L);

        verify(accommodationRepository).findById(1L);
        assertNotNull(result);
        List<PricelistItem> arrayList = result.getPriceList().stream().toList();

        assertEquals(arrayList.get(0).getPrice(), 1000);
        assertEquals(arrayList.get(0).getTimeSlot().getStartDate(), LocalDate.parse("2024-02-10"));
        assertEquals(arrayList.get(0).getTimeSlot().getEndDate(), LocalDate.parse("2024-02-20"));
    }

    //ovde pisem testove za automatsku potvrdu

    @Test
    public void changeFreeTimeSlotsWithInvalidAccommodationIdTest(){
        TimeSlotDTO reservationTimeSlot = new TimeSlotDTO(LocalDate.parse("2024-01-25"), LocalDate.parse("2024-02-02"));

        when(accommodationRepository.findById(3L)).thenReturn(Optional.empty());

        Accommodation result = accommodationService.changeFreeTimeSlotsAcceptingReservation(3L, reservationTimeSlot);

        verify(accommodationRepository).findById(3L);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoInteractions(timeSlotRepository);
        assertNull(result);
    }



    //testiramo da li radi funkcija da li izbacuje datume rezervacije iz accommodation freetimeslots liste
    @MethodSource(value = "getValidDatesForReservation")
    @ParameterizedTest
    public void changeFreeTimeSlotsSuccessfullTest(
            LocalDate startDate, LocalDate endDate, ArrayList<TimeSlot> expectedNewFreeTimeSlots) {

        //dobijamo timeslot iz methodsource-a
        TimeSlotDTO reservationTimeSlot = new TimeSlotDTO(startDate, endDate);

        //pravimo accommodation i setujemo id i freeTimeSlots
        Accommodation reservedAccommodation = new Accommodation();
        reservedAccommodation.setId(1L);
        Collection<TimeSlot> freeTimeSlots = new ArrayList<>();
        freeTimeSlots.add(getValidDatesForFreeTimeSlots());
        reservedAccommodation.setFreeTimeSlots(freeTimeSlots);

        //mokujemo repozitorijume
        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(reservedAccommodation));
        when(accommodationRepository.save(reservedAccommodation)).thenReturn(reservedAccommodation);
        when(timeSlotRepository.save(any(TimeSlot.class))).thenReturn(any(TimeSlot.class));

        //pozovemo funkciju koju testiramo
        Accommodation accommodation = accommodationService.changeFreeTimeSlotsAcceptingReservation(1L, reservationTimeSlot);

        //proverimo da li su se pozvale mokovane metode
        verify(accommodationRepository).findById(1L);
        verify(timeSlotRepository, times(expectedNewFreeTimeSlots.size())).save(any(TimeSlot.class));
        verify(accommodationRepository).save(accommodation);
        verifyNoMoreInteractions(accommodationRepository);
        verifyNoMoreInteractions(timeSlotRepository);

        //proverimo da li smo dobili iste liste
        assertEquals(expectedNewFreeTimeSlots.size(), accommodation.getFreeTimeSlots().size());

        for(int i=0;i<accommodation.getFreeTimeSlots().size();i++){
            List<TimeSlot> timeSlotsActual = accommodation.getFreeTimeSlots().stream().toList();

            TimeSlot timeSlotExpected = expectedNewFreeTimeSlots.get(i);
            TimeSlot timeSlotActual = timeSlotsActual.get(i);

            assertEquals(timeSlotExpected.getStartDate(),timeSlotActual.getStartDate());
            assertEquals(timeSlotExpected.getEndDate(),timeSlotActual.getEndDate());
        }
    }

    private TimeSlot getValidDatesForFreeTimeSlots() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusDays(5);
        LocalDate endDate = now.plusDays(15);
        return new TimeSlot(startDate, endDate);
    }

    List<Arguments> getValidDatesForReservation(){
        LocalDate now = LocalDate.now();

        //za prvi argument
        ArrayList<TimeSlot> emptyFreeTimeSlots = new ArrayList<>();

        //za drugi argument
        ArrayList<TimeSlot> newFreeTimeSlots = new ArrayList<>();
        TimeSlot timeSlot1 = new TimeSlot(now.plusDays(5),now.plusDays(7));
        TimeSlot timeSlot2 = new TimeSlot(now.plusDays(13),now.plusDays(15));
        newFreeTimeSlots.add(timeSlot1);
        newFreeTimeSlots.add(timeSlot2);

        //za treci argument
        ArrayList<TimeSlot> newFreeTimeSlots1 = new ArrayList<>();
        TimeSlot timeSlot3 = new TimeSlot(now.plusDays(11),now.plusDays(15));
        newFreeTimeSlots1.add(timeSlot3);

        //za cetvrti argument
        ArrayList<TimeSlot> newFreeTimeSlots2 = new ArrayList<>();
        TimeSlot timeSlot4 = new TimeSlot(now.plusDays(5),now.plusDays(6));
        newFreeTimeSlots2.add(timeSlot4);


        return Arrays.asList(Arguments.arguments(now.plusDays(5),now.plusDays(15),emptyFreeTimeSlots),
                Arguments.arguments(now.plusDays(8),now.plusDays(12),newFreeTimeSlots),
                Arguments.arguments(now.plusDays(5),now.plusDays(10),newFreeTimeSlots1),
                Arguments.arguments(now.plusDays(7),now.plusDays(15),newFreeTimeSlots2));
    }

}