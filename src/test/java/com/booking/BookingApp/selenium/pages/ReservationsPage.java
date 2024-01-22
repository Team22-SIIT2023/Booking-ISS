package com.booking.BookingApp.selenium.pages;

import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.repository.AccommodationRepository;
import jakarta.persistence.Tuple;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class ReservationsPage {

    private WebDriver driver;

    private static int deadlineAccommodation1 = 2;

    private static int deadlineAccommodation2 = 7;

    private static int deadlineAccommodation3 = 7;
    int numberOfReservation;

    @FindBy(xpath = "(//tr[@mat-row])[5]//button[@id='cancelBtn']")
    private WebElement fifthCancelButton;

    @FindBy(xpath = "//*[@id='requestsTable']/tbody")
    private WebElement tableData;

    @FindBy(xpath = ".//tbody/tr")
    private List<WebElement> tableRows;

    @FindBy(xpath = "/html/body/app-root/app-nav-bar/mat-toolbar/div[2]/button[1]")
    private WebElement homeButton;

    @FindBy(xpath = "/html/body/app-root/app-nav-bar/mat-toolbar/div[2]/button[2]")
    private WebElement reservationButton;

    @FindBy(xpath = "//*[@id='requestsTable']/tbody/tr[1]")
    private WebElement tableElement;

    @FindBy(xpath = "//*[@id='requestsTable']/tbody/tr[1]/td[1]/text()[1]")
    private WebElement startDate;

    @FindBy(xpath = "//*[@id='requestsTable']/tbody/tr[1]/td[1]")
    private WebElement endDate;


    @FindBy(xpath = "//*[@id='requestsTableDiv']/mat-paginator/div/div/div[2]/button[3]")
    private WebElement nextReservationPage;

    @FindBy(xpath = "//*[@id='requestsTableDiv']/mat-paginator/div/div/div[2]/div")
    private WebElement numberOfReservations;

    @FindBy(xpath = "//*[@id='homeBtn']")
    private WebElement bookNowButton;


    @FindBy(xpath = "//*[@id='mat-tab-label-0-1']")
    private WebElement requestsTab;

    @FindBy(xpath = "/html/body/app-root/app-view-accommodations/div[4]/div[1]/app-accommodation-card/div/div/div[3]/button")
    private static String PAGE_URL = "http://localhost:4200/reservations";

    private JavascriptExecutor jsExecutor;


    public ReservationsPage(WebDriver driver) {
        this.driver = driver;
        this.jsExecutor = (JavascriptExecutor) driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public void nextPage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(nextReservationPage));
            Actions actions = new Actions(driver);
            actions.moveToElement(nextReservationPage).perform();
            nextReservationPage.click();
        } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
        }
    }

    public boolean isThereMore(){
        String numbers = numberOfReservations.getText();
        String[] tokens = numbers.split(" ");
        return tokens[3].equals(tokens[4]);
    };

    public int getNumberOfReservation(){
        String numbers = numberOfReservations.getText();
        String[] tokens = numbers.split(" ");
        return Integer.parseInt(tokens[4]);
    };

    public int getNumberOfRequests(){
        requestsTab.click();
        String numbers = numberOfReservations.getText();
        String[] tokens = numbers.split(" ");
        return Integer.parseInt(tokens[4]);
    }
    public int getStartNumberOfReservations() {
        (new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS))).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(".//tbody/tr"), 0));
        numberOfReservation = getNumberOfReservation();
        return numberOfReservation;
    }

    public int getStartNumberOfRequests() {
        (new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS))).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(".//tbody/tr"), 0));
        numberOfReservation = getNumberOfRequests();
        return numberOfReservation;
    }

    public void navigateToRequests(){
        requestsTab.click();
    };

    public void refreshReservations(){
        homeButton.click();
        reservationButton.click();
    }

    public int getEndNumberOfReservationAfterCancelling() {
        (new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS))).until(ExpectedConditions.visibilityOf(tableData));
        return numberOfReservation-1;
    }

    public int getEndNumberOfReservationFailedCancelling() {
        (new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS))).until(ExpectedConditions.visibilityOf(tableData));
        return numberOfReservation;
    }
    public Map<String,TimeSlot> cancelReservation() {
        Map<String,TimeSlot> map = new HashMap<>();

        List<WebElement> rows = tableData.findElements(By.xpath(".//tr"));

        for (WebElement row : rows) {
            WebElement dateCell = row.findElement(By.xpath(".//td[@mat-cell]"));
            WebElement accommodationNameCell = row.findElement(By.xpath(".//td[3]"));
            WebElement cancelBtn = row.findElement(By.xpath(".//button[contains(@id, 'cancelBtn')]"));

            String accommodationNameAndRateReview = accommodationNameCell.getText().trim();

            String[] accommodationNameTokens = accommodationNameAndRateReview.split("\n");
            String accommodationName = accommodationNameTokens[0];

            String dateText = dateCell.getText().trim();

            String[] dateValues = dateText.split("\n");

            String startDateText = dateValues[0].trim();
            String endDateText = dateValues[1].trim();

            String[] startDateTokens = startDateText.split(":");
            String[] endDateTokens = endDateText.split(":");

            String startDateOnly = startDateTokens[1];
            String endDateOnly = endDateTokens[1];

            LocalDate startDate = parseToLocalDate(startDateOnly);
            LocalDate endDate = parseToLocalDate(endDateOnly);

            System.out.println("Start Date: " + startDate);
            System.out.println("End Date: " + endDate);
            System.out.println("Accommodation name: " + accommodationName);

            LocalDate now = LocalDate.now();

            System.out.println(now.isBefore(startDate.minusDays(deadlineAccommodation1)));
            TimeSlot timeSlot = new TimeSlot(startDate,endDate);

            if(accommodationName.trim().equals("Accommodation Name 1")){
                if(now.isBefore(startDate.minusDays(deadlineAccommodation1))){
                    cancelBtn.click();
                    map.put(accommodationName,timeSlot);
                    return map;
                }
            }
            if(accommodationName.trim().equals("Accommodation Name 2")){
                if(now.isBefore(startDate.minusDays(deadlineAccommodation2))){
                    cancelBtn.click();
                    map.put(accommodationName,timeSlot);
                    return map;
                }
            }
            if(accommodationName.trim().equals("Accommodation Name 3")){
                if(now.isBefore(startDate.minusDays(deadlineAccommodation3))){
                    cancelBtn.click();
                    map.put(accommodationName,timeSlot);
                    return map;
                }
            }
        }
        return map;

    }

    public boolean cancelReservationWithBadDeadline() {
        List<WebElement> rows = tableData.findElements(By.xpath(".//tr"));

        for (WebElement row : rows) {
            WebElement dateCell = row.findElement(By.xpath(".//td[@mat-cell]"));
            WebElement accommodationNameCell = row.findElement(By.xpath(".//td[3]"));
            WebElement cancelBtn = row.findElement(By.xpath(".//button[contains(@id, 'cancelBtn')]"));

            String accommodationNameAndRateReview = accommodationNameCell.getText().trim();

            String[] accommodationNameTokens = accommodationNameAndRateReview.split("\n");
            String accommodationName = accommodationNameTokens[0];

            String dateText = dateCell.getText().trim();

            String[] dateValues = dateText.split("\n");

            String startDateText = dateValues[0].trim();
            String endDateText = dateValues[1].trim();

            String[] startDateTokens = startDateText.split(":");
            String[] endDateTokens = endDateText.split(":");

            String startDateOnly = startDateTokens[1];
            String endDateOnly = endDateTokens[1];

            LocalDate startDate = parseToLocalDate(startDateOnly);
            LocalDate endDate = parseToLocalDate(endDateOnly);

            System.out.println("Start Date: " + startDate);
            System.out.println("End Date: " + endDate);
            System.out.println("Accommodation name: " + accommodationName);

            LocalDate now = LocalDate.now();

            System.out.println(now.isBefore(startDate.minusDays(deadlineAccommodation1)));

            if (accommodationName.trim().equals("Accommodation Name 1")) {
                if (!now.isBefore(startDate.minusDays(deadlineAccommodation1))) {
                    cancelBtn.click();
                    return true;
                }
            }
            if (accommodationName.trim().equals("Accommodation Name 2")) {
                if (!now.isBefore(startDate.minusDays(deadlineAccommodation2))) {
                    cancelBtn.click();
                    return true;
                }
            }
            if (accommodationName.trim().equals("Accommodation Name 3")) {
                if (!now.isBefore(startDate.minusDays(deadlineAccommodation3))) {
                    cancelBtn.click();
                    return true;
                }
            }
        }
        return false;
    }

    private LocalDate parseToLocalDate(String dateText) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateText, formatter);
    }

    public void cancelCreatedReservation() {
        LocalDate now = LocalDate.now();
        LocalDate reservationStartDate = now.plusDays(4);
        LocalDate reservationEndDate = now.plusDays(6);

        List<WebElement> rows = tableData.findElements(By.xpath(".//tr"));

        for (WebElement row : rows) {
            WebElement dateCell = row.findElement(By.xpath(".//td[@mat-cell]"));
            WebElement accommodationNameCell = row.findElement(By.xpath(".//td[3]"));
            WebElement cancelBtn = row.findElement(By.xpath(".//button[contains(@id, 'cancelBtn')]"));

            String accommodationNameAndRateReview = accommodationNameCell.getText().trim();

            String[] accommodationNameTokens = accommodationNameAndRateReview.split("\n");
            String accommodationName = accommodationNameTokens[0];

            String dateText = dateCell.getText().trim();

            String[] dateValues = dateText.split("\n");

            String startDateText = dateValues[0].trim();
            String endDateText = dateValues[1].trim();

            String[] startDateTokens = startDateText.split(":");
            String[] endDateTokens = endDateText.split(":");

            String startDateOnly = startDateTokens[1];
            String endDateOnly = endDateTokens[1];

            LocalDate startDate = parseToLocalDate(startDateOnly);
            LocalDate endDate = parseToLocalDate(endDateOnly);

            System.out.println("Start Date: " + startDate);
            System.out.println("End Date: " + endDate);
            System.out.println("Accommodation name: " + accommodationName);


            if (accommodationName.trim().equals("Accommodation Name 1")) {
                if (startDate.equals(reservationStartDate) && endDate.equals(reservationEndDate)) {
                    cancelBtn.click();
                    return;
                }
            }
        }

    }

    public void acceptReservationRequest1() {
        requestsTab.click();
        String numbers = numberOfReservations.getText();
        String[] tokens = numbers.split(" ");
        int number = Integer.parseInt(tokens[4]);
        System.out.println(number);
        int cycle = (number + 4) / 5;
        for(int i = 0; i<cycle; i++){
            if(i>1){
                nextPage();
            }
        }

        LocalDate now = LocalDate.now();
        LocalDate reservationStartDate = now.plusDays(4);
        LocalDate reservationEndDate = now.plusDays(6);

        List<WebElement> rows = tableData.findElements(By.xpath(".//tr"));

        for (WebElement row : rows) {
            try {
                WebElement dateCell = row.findElement(By.xpath(".//td[@mat-cell]"));
                WebElement accommodationNameCell = row.findElement(By.xpath(".//td[6]"));
                WebElement statusCell = row.findElement(By.xpath(".//td[7]"));
                WebElement acceptBtn = row.findElement(By.xpath(".//button[@id='acceptRequestBtn']"));

                String status = statusCell.getText().trim();

                String accommodationNameAndRateReview = accommodationNameCell.getText().trim();
                String dateText = dateCell.getText().trim();

                String[] accommodationNameTokens = accommodationNameAndRateReview.split("\n");
                String accommodationName = accommodationNameTokens[0];

                String[] dateValues = dateText.split("\n");
                String startDateText = dateValues[0].trim();
                String endDateText = dateValues[1].trim();

                String[] startDateTokens = startDateText.split(":");
                String[] endDateTokens = endDateText.split(":");

                String startDateOnly = startDateTokens[1];
                String endDateOnly = endDateTokens[1];

                LocalDate startDate = parseToLocalDate(startDateOnly);
                LocalDate endDate = parseToLocalDate(endDateOnly);

                System.out.println("Accommodation name: " + accommodationName);
                System.out.println("Start Date: " + startDate);
                System.out.println("End Date: " + endDate);

                if (accommodationName.trim().equals("Accommodation Name 1") &&
                        startDate.equals(reservationStartDate) &&
                        endDate.equals(reservationEndDate) && status.equals("PENDING")) {
                    System.out.println("PRIHVACENO");
                    acceptBtn.click();
                    return;
                }
            } catch (StaleElementReferenceException e) {
                // Ignorisanje izuzetka i nastavak iteracije
            }
        }
    }

    public void acceptReservationRequest() {
        requestsTab.click();

        LocalDate now = LocalDate.now();
        LocalDate reservationStartDate = now.plusDays(4);
        LocalDate reservationEndDate = now.plusDays(6);

        List<WebElement> rows = tableData.findElements(By.xpath(".//tr"));

        for (WebElement row : rows) {
            WebElement dateCell = row.findElement(By.xpath(".//td[@mat-cell]"));
            WebElement accommodationNameCell = row.findElement(By.xpath(".//td[3]"));
            WebElement acceptButton = row.findElement(By.xpath(".//button[contains(@id, 'acceptRequestBtn')]"));

            String accommodationNameAndRateReview = accommodationNameCell.getText().trim();

            String[] accommodationNameTokens = accommodationNameAndRateReview.split("\n");
            String accommodationName = accommodationNameTokens[0];

            String dateText = dateCell.getText().trim();

            String[] dateValues = dateText.split("\n");

            String startDateText = dateValues[0].trim();
            String endDateText = dateValues[1].trim();

            String[] startDateTokens = startDateText.split(":");
            String[] endDateTokens = endDateText.split(":");

            String startDateOnly = startDateTokens[1];
            String endDateOnly = endDateTokens[1];

            LocalDate startDate = parseToLocalDate(startDateOnly);
            LocalDate endDate = parseToLocalDate(endDateOnly);

            System.out.println("Start Date: " + startDate);
            System.out.println("End Date: " + endDate);
            System.out.println("Accommodation name: " + accommodationName);


            if (accommodationName.trim().equals("Accommodation Name 1")) {
                if (startDate.equals(reservationStartDate) && endDate.equals(reservationEndDate)) {
                    acceptButton.click();
                    return;
                }
            }
        }
    }

    public boolean isDeclinedTheOther() {
        requestsTab.click();

        String numbers = numberOfReservations.getText();
        String[] tokens = numbers.split(" ");
        int number = Integer.parseInt(tokens[4]);
        System.out.println(number);
        int cycle = (number + 4) / 5;
        for(int i = 0; i<cycle; i++){
            if(i>1){
                nextPage();
            }
        }


        LocalDate now = LocalDate.now();
        LocalDate reservationStartDate = now.plusDays(5);
        LocalDate reservationEndDate = now.plusDays(7);

        List<WebElement> rows = tableData.findElements(By.xpath(".//tr"));

        for (WebElement row : rows) {
            try {
                WebElement dateCell = row.findElement(By.xpath(".//td[@mat-cell]"));
                WebElement accommodationNameCell = row.findElement(By.xpath(".//td[6]"));
                WebElement statusCell = row.findElement(By.xpath(".//td[7]"));

                String status = statusCell.getText().trim();
                System.out.println("Status: " + status);

                String accommodationNameAndRateReview = accommodationNameCell.getText().trim();
                String dateText = dateCell.getText().trim();

                String[] accommodationNameTokens = accommodationNameAndRateReview.split("\n");
                String accommodationName = accommodationNameTokens[0];

                String[] dateValues = dateText.split("\n");
                String startDateText = dateValues[0].trim();
                String endDateText = dateValues[1].trim();

                String[] startDateTokens = startDateText.split(":");
                String[] endDateTokens = endDateText.split(":");

                String startDateOnly = startDateTokens[1];
                String endDateOnly = endDateTokens[1];

                LocalDate startDate = parseToLocalDate(startDateOnly);
                LocalDate endDate = parseToLocalDate(endDateOnly);

                System.out.println("Accommodation name: " + accommodationName);
                System.out.println("Start Date: " + startDate);
                System.out.println("End Date: " + endDate);

                if (accommodationName.trim().equals("Accommodation Name 1") &&
                        startDate.equals(reservationStartDate) &&
                        endDate.equals(reservationEndDate) && status.equals("DENIED")) {
                        return true;
                }
            } catch (StaleElementReferenceException e) {
                // Ignorisanje izuzetka i nastavak iteracije
            }
        }
        return false;
    }
}
