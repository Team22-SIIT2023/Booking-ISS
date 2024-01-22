package com.booking.BookingApp.selenium.tests;


import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.repository.AccommodationRepository;
import com.booking.BookingApp.selenium.pages.AccommodationsPage;
import com.booking.BookingApp.selenium.pages.EditTimeSlotsAccommodationPage;
import com.booking.BookingApp.selenium.pages.LoginPage;
import com.booking.BookingApp.selenium.pages.ReservationsPage;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CancelingReservationTest extends TestBase {

    //kada rezervacija moze da se ponisti
    //cancel rezervacije koja je kreirana dok je ukljucena automatska potvrda
    @Test
    public void cancelingReservationWithAutomaticConfirmationAndDeadlineIsNotExpired(){
        //prijavljujemo se kao domacin
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("zika@example.com","123");

        //proverava da li je ukljucena automatksa potvrda
        AccommodationsPage accommodationsPage = new AccommodationsPage(driver);
        assertTrue(accommodationsPage.pageLoaded());
        accommodationsPage.clickOnDetailsForCreatingReservation();

        //ako nije ukljucuje je
        if(!accommodationsPage.isAutomaticConfirmation()){
            accommodationsPage.changeToAutomaticConfirmation();
        }

        //prijavljujemo se kao gost
        LoginPage loginPage1 = new LoginPage(driver);
        loginPage1.login("mika@example.com","123");

        //ulazi na stranicu koja prikazuje detalje o smestaju koji rezervise
        AccommodationsPage accommodationsPage1 = new AccommodationsPage(driver);
        assertTrue(accommodationsPage1.pageLoaded());
        accommodationsPage1.clickOnDetailsForCancelledReservation("Accommodation Name 1");

        //pravi rezervaciju
        accommodationsPage1.createReservationRequest();

        //ide da je odbije - ujedno test za automatsku potvrdu sto znaci da je zahtev za rezervaciju
        //pri kreiranju odmah automatski prihvacen
        ReservationsPage reservationsPage = new ReservationsPage(driver);
        int numberOfReservationInTheStart = reservationsPage.getStartNumberOfReservations();
        int cycle = (numberOfReservationInTheStart + 4) / 5;
        for(int i = 0; i<cycle; i++){
            if(i>=1){
                reservationsPage.nextPage();
            }
        }
        //odbijamo rezervaciju
        reservationsPage.cancelCreatedReservation();

        //udjemo da pokusamo opet da rezervisemo kako bi videli da su se datumi odbijene
        // rezervacije oslobodili
        AccommodationsPage accommodationsPage2 = new AccommodationsPage(driver);
        assertTrue(accommodationsPage2.pageLoaded());

        LocalDate now = LocalDate.now();
        LocalDate reservationStartDate = now.plusDays(4);
        LocalDate reservationEndDate = now.plusDays(6);
        TimeSlot timeSlot = new TimeSlot(reservationStartDate,reservationEndDate);
        accommodationsPage2.clickOnDetailsForCancelledReservation("Accommodation Name 1");
        boolean areDatesValid = accommodationsPage2.checkIfDatesAreAvailable(timeSlot);
        assertTrue(areDatesValid);
    }


    //kada rezervacija ne moze da se ponisti
    //cancel rezervacije koja je kreirana dok je ukljucena automatska potvrda
    @Test
    public void cancelingReservationWithAutomaticConfirmationAndDeadlineIsExpired(){
        //prijavljujemo se kao domacin
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("zika@example.com","123");

        //proverava da li je ukljucena automatksa potvrda
        AccommodationsPage accommodationsPage = new AccommodationsPage(driver);
        assertTrue(accommodationsPage.pageLoaded());
        accommodationsPage.clickOnDetailsForCreatingReservation();

        //ako nije ukljucuje je
        if(!accommodationsPage.isAutomaticConfirmation()){
            accommodationsPage.changeToAutomaticConfirmation();
        }

        //prijavljujemo se kao gost
        LoginPage loginPage1 = new LoginPage(driver);
        loginPage1.login("mika@example.com","123");

        //ulazi na stranicu koja prikazuje detalje o smestaju koji rezervise
        AccommodationsPage accommodationsPage1 = new AccommodationsPage(driver);
        assertTrue(accommodationsPage1.pageLoaded());
        accommodationsPage1.clickOnDetailsForCancelledReservation("Accommodation Name 1");

        //pravi rezervaciju
        accommodationsPage1.createReservationRequestThatCanNotBeCancelled();

        //ide da je odbije - ujedno test za automatsku potvrdu sto znaci da je zahtev za rezervaciju
        //pri kreiranju odmah automatski prihvacen
        ReservationsPage reservationsPage = new ReservationsPage(driver);
        int numberOfReservationInTheStart = reservationsPage.getStartNumberOfReservations();
        int cycle = (numberOfReservationInTheStart + 4) / 5;
        for(int i = 0; i<cycle; i++){
            if(i>=1){
                reservationsPage.nextPage();
            }
        }
        //odbijamo rezervaciju - ne bi trebalo da se odbije
        reservationsPage.cancelCreatedReservation();

        //udjemo da pokusamo opet da rezervisemo kako bi videli da li su se datumi odbijene
        // rezervacije oslobodili
        AccommodationsPage accommodationsPage2 = new AccommodationsPage(driver);
        assertTrue(accommodationsPage2.pageLoaded());

        LocalDate now = LocalDate.now();
        LocalDate reservationStartDate = now.plusDays(1);
        LocalDate reservationEndDate = now.plusDays(3);
        TimeSlot timeSlot = new TimeSlot(reservationStartDate,reservationEndDate);
        accommodationsPage2.clickOnDetailsForCancelledReservation("Accommodation Name 1");
        boolean areDatesValid = accommodationsPage2.checkIfDatesAreAvailable(timeSlot);

        //ne bi trebalo da se oslobode jer nije uspesno odbijena - zbog cancel deadline-a
        assertFalse(areDatesValid);
    }


    //kada rezervacija moze da se ponisti
    //cancel rezervacije koja je kreirana dok je ukljucena rucna potvrda
    @Test
    public void cancelingReservationWithManualConfirmationAndDeadlineIsNotExpired(){
        //prijavimo se kao domacin da proverimo da li je ukljucena rucna potvrda
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("zika@example.com","123");

        AccommodationsPage accommodationsPage = new AccommodationsPage(driver);
        assertTrue(accommodationsPage.pageLoaded());
        accommodationsPage.clickOnDetailsForCreatingReservation();

        //ukoliko je ukljucena automatska potvrda ukljucujemo rucnu potvrdu
        if(accommodationsPage.isAutomaticConfirmation()){
            accommodationsPage.changeToManualConfirmation();
        }

        //prijavljujemo se kao gost da kreiramo zahtev za rezervaciju
        LoginPage loginPage1 = new LoginPage(driver);
        loginPage1.login("mika@example.com","123");

        AccommodationsPage accommodationsPage1 = new AccommodationsPage(driver);
        assertTrue(accommodationsPage1.pageLoaded());
        accommodationsPage1.clickOnDetailsForCancelledReservation("Accommodation Name 1");
        accommodationsPage1.createReservationRequest1();

        //prijavljujemo se kao domacin da odobrimo zahtev za rezervaciju
        LoginPage loginPage2 = new LoginPage(driver);
        loginPage2.login("zika@example.com","123");

        ReservationsPage reservationsPage = new ReservationsPage(driver);
        reservationsPage.navigateToRequests();
        int numberOfReservationInTheStart = 6;
        int cycle = (numberOfReservationInTheStart + 4) / 5;
        for(int i = 0; i<cycle; i++){
            if(i>=1){
                reservationsPage.nextPage();
            }
        }
        reservationsPage.acceptReservationRequest1();


        //prijavljujemo se kao gost da ponistimo rezervaciju - rok za ponistavanje rezervacije
        // je 2 dana pre pocetka rezervacije pa je ovo rezervacija koja moze da se ponisti
        LoginPage loginPage3 = new LoginPage(driver);
        loginPage3.login("mika@example.com","123");

        ReservationsPage reservationsPage1 = new ReservationsPage(driver);
        int numberOfReservationInTheStart1 = reservationsPage.getStartNumberOfReservations();
        int cycle1 = (numberOfReservationInTheStart1 + 4) / 5;
        for(int i = 0; i<cycle1; i++){
            if(i>=1){
                reservationsPage1.nextPage();
            }
        }
        reservationsPage.cancelCreatedReservation();

        //ulazimo da pokusamo opet da rezervisemo -proveravamo da li je smestaj dostupan za datume koji
        //se nalaze u ponistenoj rezervaciji
        AccommodationsPage accommodationsPage2 = new AccommodationsPage(driver);
        assertTrue(accommodationsPage2.pageLoaded());

        LocalDate now = LocalDate.now();
        LocalDate reservationStartDate = now.plusDays(4);
        LocalDate reservationEndDate = now.plusDays(6);
        TimeSlot timeSlot = new TimeSlot(reservationStartDate,reservationEndDate);
        accommodationsPage2.clickOnDetailsForCancelledReservation("Accommodation Name 1");
        boolean areDatesValid = accommodationsPage2.checkIfDatesAreAvailable(timeSlot);
        assertTrue(areDatesValid);
    }

    //kada rezervacija moze da se ponisti
    //cancel rezervacije koja je kreirana dok je ukljucena rucna potvrda
    @Test
    public void cancelingReservationWithManualConfirmationAndDeadlineIsExpired(){
        //prijavimo se kao domacin da proverimo da li je ukljucena rucna potvrda
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("zika@example.com","123");

        AccommodationsPage accommodationsPage = new AccommodationsPage(driver);
        boolean loaded=accommodationsPage.pageLoaded();
        assertTrue(loaded);

        accommodationsPage.clickOnDetailsForCreatingReservation();

        //ukoliko je ukljucena automatska potvrda ukljucujemo rucnu potvrdu
        if(accommodationsPage.isAutomaticConfirmation()){
            accommodationsPage.changeToManualConfirmation();
        }

        //prijavljujemo se kao gost da kreiramo zahtev za rezervaciju
        LoginPage loginPage1 = new LoginPage(driver);
        loginPage1.login("mika@example.com","123");

        AccommodationsPage accommodationsPage1 = new AccommodationsPage(driver);
        assertTrue(accommodationsPage1.pageLoaded());
        accommodationsPage1.clickOnDetailsForCancelledReservation("Accommodation Name 1");
        accommodationsPage1.createReservationRequestThatCanNotBeCancelled();

        //prijavljujemo se kao domacin da odobrimo zahtev za rezervaciju
        LoginPage loginPage2 = new LoginPage(driver);
        loginPage2.login("zika@example.com","123");

        ReservationsPage reservationsPage = new ReservationsPage(driver);
        reservationsPage.navigateToRequests();
        int numberOfReservationInTheStart = 6;
        int cycle = (numberOfReservationInTheStart + 4) / 5;
        for(int i = 0; i<cycle; i++){
            if(i>=1){
                reservationsPage.nextPage();
            }
        }
        reservationsPage.acceptReservationRequest1();


        //prijavljujemo se kao gost da ponistimo rezervaciju - rok za ponistavanje rezervacije
        // je 2 dana pre pocetka rezervacije pa je ovo rezervacija koja moze da se ponisti
        LoginPage loginPage3 = new LoginPage(driver);
        loginPage3.login("mika@example.com","123");

        ReservationsPage reservationsPage1 = new ReservationsPage(driver);
        int numberOfReservationInTheStart1 = reservationsPage.getStartNumberOfReservations();
        int cycle1 = (numberOfReservationInTheStart1 + 4) / 5;
        for(int i = 0; i<cycle1; i++){
            if(i>=1){
                reservationsPage1.nextPage();
            }
        }
        reservationsPage.cancelCreatedReservation();

        //ulazimo da pokusamo opet da rezervisemo -proveravamo da li je smestaj dostupan za datume koji
        //se nalaze u ponistenoj rezervaciji
        AccommodationsPage accommodationsPage2 = new AccommodationsPage(driver);
        assertTrue(accommodationsPage2.pageLoaded());

        LocalDate now = LocalDate.now();
        LocalDate reservationStartDate = now.plusDays(1);
        LocalDate reservationEndDate = now.plusDays(3);
        TimeSlot timeSlot = new TimeSlot(reservationStartDate,reservationEndDate);
        accommodationsPage2.clickOnDetailsForCancelledReservation("Accommodation Name 1");
        boolean areDatesValid = accommodationsPage2.checkIfDatesAreAvailable(timeSlot);
        assertTrue(areDatesValid);
    }


}
