package com.booking.BookingApp.selenium.tests;

import com.booking.BookingApp.selenium.pages.AccommodationDetailsPage;
import com.booking.BookingApp.selenium.pages.EditTimeSlotsAccommodationPage;
import com.booking.BookingApp.selenium.pages.LoginPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EditAccommodationTest extends TestBase{


    @Test
    public void testEditPrice() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("zika@example.com","123");

        EditTimeSlotsAccommodationPage page = new EditTimeSlotsAccommodationPage(driver);
        int sizeBefore = page.getStartTableSize();
        System.out.println(sizeBefore);
        page.inputPrice(10000);
        page.inputDateForPrice("2025","MAR", "18", "20");
        page.clickApplyForPrice();
        assertTrue(page.acceptPrice());
        int sizeAfter = page.getEndTableSize();
        System.out.println(sizeAfter);

        assertEquals(sizeBefore, sizeAfter-1);
    }

    @Test
    public void testEditPriceWithEqualsDate() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("zika@example.com","123");

        EditTimeSlotsAccommodationPage page = new EditTimeSlotsAccommodationPage(driver);
        int sizeBefore = page.getStartTableSize();
        System.out.println(sizeBefore);
        page.inputPrice(10000);
        page.inputDateForPrice("2024","SEP", "22", "26");
        page.clickApplyForPrice();
//        page.acceptPrice();
        assertTrue(page.acceptPrice());

        int sizeAfter = page.getEndTableSizeEqualsDate();
        System.out.println(sizeAfter);

        assertEquals(sizeBefore, sizeAfter);
    }

    @Test
    public void testEditPriceWithDateOverlaps() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("zika@example.com","123");

        EditTimeSlotsAccommodationPage page = new EditTimeSlotsAccommodationPage(driver);
        int sizeBefore = page.getStartTableSize();
        System.out.println(sizeBefore);
        page.inputPrice(10000);
        page.inputDateForPrice("2024","JAN", "27", "31");
        page.clickApplyForPrice();
        page.acceptPrice();
//        assertTrue(page.acceptPrice());

        int sizeAfter = page.getEndTableSize();
        System.out.println(sizeAfter);

        assertEquals(sizeBefore, sizeAfter-1);
    }


    @Test
    public void testEditPriceWithNegativePrice() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("zika@example.com","123");

        EditTimeSlotsAccommodationPage page = new EditTimeSlotsAccommodationPage(driver);
        int sizeBefore = page.getStartTableSize();
        System.out.println(sizeBefore);
        page.inputPrice(-10000);
        page.inputDateForPrice("2024","JAN", "27", "31");
        page.clickApplyForPrice();
//        page.acceptPrice();
        assertFalse(page.acceptPrice());
    }


    @Test
    public void testValidEditFreeDates() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("zika@example.com","123");

        EditTimeSlotsAccommodationPage page = new EditTimeSlotsAccommodationPage(driver);
        page.inputDateForFreeDates("2026", "MAR", "10", "15");
        page.clickApplyForFreeDates();
        page.acceptFreeDates();

        page.logout();
        loginPage.login("mika@example.com","123");

        AccommodationDetailsPage accommodationDetailsPage = new AccommodationDetailsPage(driver);

        boolean datesFilter = accommodationDetailsPage.checkFilteredDates("2026", "MAR", "10", "15");
        assertTrue(datesFilter);
    }

    @Test
    public void testInalidEditFreeDates() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("zika@example.com","123");

        EditTimeSlotsAccommodationPage page = new EditTimeSlotsAccommodationPage(driver);
        page.inputDateForFreeDates("2024", "MAR", "23", "24");
        page.clickApplyForFreeDates();
        page.acceptFreeDates();

        page.logout();
        loginPage.login("mika@example.com","123");

        AccommodationDetailsPage accommodationDetailsPage = new AccommodationDetailsPage(driver);

        boolean datesFilter = accommodationDetailsPage.checkFilteredDates("2024", "MAR", "23", "254");
        assertFalse(datesFilter);
    }
}
