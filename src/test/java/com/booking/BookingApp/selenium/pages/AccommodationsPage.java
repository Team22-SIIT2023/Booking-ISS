package com.booking.BookingApp.selenium.pages;

import com.booking.BookingApp.domain.TimeSlot;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class AccommodationsPage {

    private WebDriver driver;
    private JavascriptExecutor jsExecutor;

    private static String PAGE_URL = "http://localhost:4200/home/accommodations";

    @FindBy(css = "input[formControlName='destination']")
    private WebElement destinationInput;

    @FindBy(css = "mat-select[formControlName='accommodationType']")
    private WebElement typeSelect;

    @FindBy(css = "input[formControlName='guestNum']")
    private WebElement guestNumInput;

    @FindBy(css = "input[formControlName='minValue']")
    private WebElement minSlider;

    @FindBy(css = "input[formControlName='maxValue']")
    private WebElement maxSlider;
    @FindBy(id = "disablePrice-btn")
    private WebElement disablePriceBtn;

    @FindBy(id = "search-btn")
    private WebElement searchBtn;

    @FindBy(id="clear-filters")
    private WebElement clearBtn;

    //moram da im malo menjam imena

    @FindBy(css = "#dateRangeForEditFreeDates .mat-mdc-button-touch-target")
    private WebElement datePickerForFreeDatesBtn;

    @FindBy(css=".mat-calendar")
    private WebElement dateDialog;

    @FindBy(id = "applyDateForTimeSlots")
    private WebElement btnApplyDates;

    @FindBy(xpath = "//mat-calendar-header//button[contains(@class, 'mat-calendar-period')]")
    private WebElement choseMonthAndYearBtn;

    @FindBy(xpath = "//input[@formControlName='startDateInput']")
    private WebElement startDateInput;

    @FindBy(xpath = "//input[@formControlName='endDateInput']")
    private WebElement endDateInput;

    @FindBy(id="price")
    WebElement calculatedPrice;

    @FindBy(xpath = "//*[@id='mat-input-48']")
    WebElement priceAfterReservation;

    @FindBy(xpath = "//*[@id='dateRangeForEditFreeDates']/div[1]/div[2]/div[2]/mat-datepicker-toggle/button")
    WebElement reservationCalendarButton;

    @FindBy(xpath = "/html/body/app-root/app-accommodation-details/form/div")
    WebElement selectedDatesText;

    @FindBy(xpath = "//*[@id='mat-date-range-input-31']/div/div[1]/input")
    WebElement inputForStartDate;

    @FindBy(xpath = "//*[@id=\"mat-date-range-input-31\"]/div/div[2]/input")
    WebElement inputForEndDate;

    @FindBy(xpath = "/html/body/app-root/app-accommodation-details/form/button")
    WebElement buttonReserve;

    @FindBy(xpath = "//*[@id='mat-date-range-input-31']")
    WebElement inputDates;

    @FindBy(css = ".accommodation")
    List<WebElement>accommodationCards;

    @FindBy(xpath = "//*[@id='numberSelect']/div")
    WebElement numberOfGuests;
    @FindBy(xpath = "//*[@id='mat-option-0']/span")
    WebElement firstOption;

    @FindBy(xpath = "//*[@id='applyDateForTimeSlots']")
    WebElement applyButton;

    @FindBy(xpath = "//*[@id='mat-radio-3-input']")
    WebElement manualConfirmationRadioButton;

    @FindBy(xpath = "//*[@id='mat-radio-2-input']")
    WebElement automaticConfirmationRadioButton;

    @FindBy(xpath = "//*[@id='saveApproval']")
    WebElement saveApprovalButton;

    public AccommodationsPage(WebDriver driver) {
        this.driver = driver;
        this.jsExecutor = (JavascriptExecutor) driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);

    }
    public boolean pageLoaded() {
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By.cssSelector(".accommodation"))));
        return true;
    }

    public boolean isAutomaticConfirmation(){
        return automaticConfirmationRadioButton.isSelected();
    }

    public void filterByLocationTypeAndAmenities(String name, String type,String[] amenities) {

        destinationInput.clear();
        destinationInput.sendKeys(name);

        typeSelect.click();

        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
        WebElement typeOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-option//span[contains(text(),'" + type.toUpperCase() + "')]")));
        typeOption.click();

        for(String amenity:amenities){
            WebElement checkbox=driver.findElement(By.xpath("//mat-checkbox//label[contains(text(),'"+amenity.toLowerCase()+"')]"));
            checkbox.click();
        }
        disablePriceBtn.click();
        searchBtn.click();
        clearBtn.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void filterByGuestsDatesAndPrices(String guestNum,int priceMin,int priceMax,String year, String month, String startDate, String endDate){

        guestNumInput.clear();
        guestNumInput.sendKeys(guestNum);

        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", minSlider, priceMin);
        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", minSlider);

        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", maxSlider, priceMax);
        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", maxSlider);

        inputDateForFreeDates(year, month, startDate, endDate);
        clickApplyForFreeDates();

        searchBtn.click();
        clearBtn.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public void inputDateForFreeDates(String year, String month, String startDate, String endDate) {
        datePickerForFreeDatesBtn.click();
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.visibilityOf(dateDialog));

        choseMonthAndYearBtn.click();
        String yearInp = String.format("//button/span[text()=' %s ']", year);
        driver.findElement(By.xpath(yearInp)).click();

        String monthInp = String.format("//button/span[text()=' %s ']", month);
        driver.findElement(By.xpath(monthInp)).click();

        String start = String.format("//td//span[contains(text(),'%s')]", startDate);
        String end = String.format("//td//span[contains(text(),'%s')]", endDate);

        driver.findElement(By.xpath(start)).click();
        driver.findElement(By.xpath(end)).click();

    }

    public void clickApplyForFreeDates() {
        btnApplyDates.click();
    }


    public boolean checkFilteredLocation(String location) {
        for(WebElement card:accommodationCards){
            WebElement address=card.findElement(By.cssSelector(".accommodation-address"));
            if(!address.getText().contains(location)){
                return false;
            }
        }
        return true;
    }



    public boolean checkFilteredAmenities(String[] amenities) {
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
        WebElement card=accommodationCards.get(0);
        WebElement detailBtn=card.findElement(By.cssSelector(".details-btn"));
        detailBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Available amenities')]")));
        List<WebElement>spans=driver.findElements(By.xpath("//mat-list-item[contains(@class,'amenityItem')]/span"));
        for (String amenity : amenities) {
            boolean found = false;
            for (WebElement span : spans) {
                if (span.getText().equals(amenity)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }

        return true;
    }

    public void clickOnDetailsForCancelledReservation(String accommodationName){
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(20));
        String[] accommodationNameTokens = accommodationName.split(" ");
        int accommodationNameNumber = Integer.parseInt(accommodationNameTokens[2]);
        WebElement card=accommodationCards.get(accommodationNameNumber-1);
        WebElement detailBtn=card.findElement(By.cssSelector(".details-btn"));
        detailBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='dateRangeForEditFreeDates']/div[1]/div[2]/div[2]/mat-datepicker-toggle/button")));

    }

    public void clickOnDetailsForCreatingReservation(){
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
        WebElement card=accommodationCards.get(0);
        WebElement detailBtn=card.findElement(By.cssSelector(".details-btn"));
        detailBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Available amenities')]")));

    }


    public boolean checkIfDatesAreAvailable(TimeSlot timeSlot){
        Actions actions = new Actions(driver);
        actions.moveToElement(buttonReserve);
        actions.perform();

        numberOfGuests.click();
        actions.sendKeys(Keys.ENTER).perform();

        LocalDate startDate = timeSlot.getStartDate();
        LocalDate endDate = timeSlot.getEndDate();

        WebElement startDateInput = driver.findElement(By.xpath("//input[@formcontrolname='startDateInput']"));
        WebElement endDateInput = driver.findElement(By.xpath("//input[@formcontrolname='endDateInput']"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        String startDateText = startDate.format(formatter);
        String endDateText = endDate.format(formatter);

        startDateInput.click();
        startDateInput.sendKeys(startDateText);
        endDateInput.click();
        endDateInput.sendKeys(endDateText);
        actions.sendKeys(Keys.ENTER).perform();
        reservationCalendarButton.click();
        applyButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driver -> {
            WebElement priceInput = driver.findElement(By.xpath("//input[@formcontrolname='priceInput']"));
            return !priceInput.getAttribute("value").isEmpty();
        });

        WebElement priceInput = driver.findElement(By.xpath("//input[@formcontrolname='priceInput']"));
        int price = Integer.parseInt(priceInput.getAttribute("value"));

        System.out.println(price);

        return price != 0 && buttonReserve.isEnabled();
    };


    public boolean checkFilteredPrice(int minPrice, int maxPrice) {
            for(WebElement card:accommodationCards){
                WebElement price=card.findElement(By.id("price"));
                String priceText = price.getText().split(":")[1].trim();
                int priceValue = Integer.parseInt(priceText);
                if(!(priceValue>=minPrice && priceValue<=maxPrice)){
                    return false;
                }
            }
        return true;
    }

    public boolean checkFilteredDates(String year, String month, String startDate, String endDate) {
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
        WebElement card=accommodationCards.get(0);
        WebElement detailBtn=card.findElement(By.cssSelector(".details-btn"));
        detailBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Available amenities')]")));

        try {
            inputDateForFreeDates(year, month, startDate, endDate);
            clickApplyForFreeDates();
            wait.until(ExpectedConditions.attributeToBeNotEmpty(startDateInput, "value"));
            wait.until(ExpectedConditions.attributeToBeNotEmpty(endDateInput, "value"));

        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public int countCards() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            List<WebElement> accommodationCards = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By.cssSelector(".accommodation"))));
            return accommodationCards.size();
        } catch (Exception ex) {
            return 0; // no cardsFound
        }
    }

    public void createReservationRequest() {
        Actions actions = new Actions(driver);
        actions.moveToElement(buttonReserve);
        actions.perform();

        numberOfGuests.click();
        actions.sendKeys(Keys.ENTER).perform();

        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusDays(4);
        LocalDate endDate = now.plusDays(6);

        WebElement startDateInput = driver.findElement(By.xpath("//input[@formcontrolname='startDateInput']"));
        WebElement endDateInput = driver.findElement(By.xpath("//input[@formcontrolname='endDateInput']"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        String startDateText = startDate.format(formatter);
        String endDateText = endDate.format(formatter);

        startDateInput.click();
        startDateInput.sendKeys(startDateText);
        endDateInput.click();
        endDateInput.sendKeys(endDateText);
        actions.sendKeys(Keys.ENTER).perform();
        reservationCalendarButton.click();
        applyButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driver -> {
            WebElement priceInput = driver.findElement(By.xpath("//input[@formcontrolname='priceInput']"));
            return !priceInput.getAttribute("value").isEmpty();
        });

        WebElement priceInput = driver.findElement(By.xpath("//input[@formcontrolname='priceInput']"));
        int price = Integer.parseInt(priceInput.getAttribute("value"));

        System.out.println(price);
        if(price!=0 && buttonReserve.isEnabled()){
            WebDriverWait waitSnackbar = new WebDriverWait(driver, Duration.ofSeconds(40));
            waitSnackbar.until(ExpectedConditions.invisibilityOfElementLocated(By.className("mat-mdc-snack-bar-label")));

//            applyButton.click();
            buttonReserve.click();
        }
    }

    public void createReservationRequest1() {
        Actions actions = new Actions(driver);
        actions.moveToElement(buttonReserve);
        actions.perform();

        numberOfGuests.click();
        actions.sendKeys(Keys.ENTER).perform();

        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusDays(4);
        LocalDate endDate = now.plusDays(6);

        WebElement startDateInput = driver.findElement(By.xpath("//input[@formcontrolname='startDateInput']"));
        WebElement endDateInput = driver.findElement(By.xpath("//input[@formcontrolname='endDateInput']"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        String startDateText = startDate.format(formatter);
        String endDateText = endDate.format(formatter);

        startDateInput.click();
        startDateInput.sendKeys(startDateText);
        endDateInput.click();
        endDateInput.sendKeys(endDateText);
        actions.sendKeys(Keys.ENTER).perform();
        reservationCalendarButton.click();
        applyButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driver -> {
            WebElement priceInput = driver.findElement(By.xpath("//input[@formcontrolname='priceInput']"));
            return !priceInput.getAttribute("value").isEmpty();
        });

        WebElement priceInput = driver.findElement(By.xpath("//input[@formcontrolname='priceInput']"));
        int price = Integer.parseInt(priceInput.getAttribute("value"));

        System.out.println(price);
        if(price!=0 && buttonReserve.isEnabled()){
            WebDriverWait waitSnackbar = new WebDriverWait(driver, Duration.ofSeconds(50));
            waitSnackbar.until(ExpectedConditions.invisibilityOfElementLocated(By.className("mat-mdc-snack-bar-label")));

            buttonReserve.click();
        }
    }

    public void changeToAutomaticConfirmation() {
        automaticConfirmationRadioButton.click();
        saveApprovalButton.click();
    }

    public void changeToManualConfirmation() {
        manualConfirmationRadioButton.click();
        saveApprovalButton.click();
    }

    public void createReservationRequestThatCanNotBeCancelled() {
        Actions actions = new Actions(driver);
        actions.moveToElement(buttonReserve);
        actions.perform();

        numberOfGuests.click();
        actions.sendKeys(Keys.ENTER).perform();

        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusDays(1);
        LocalDate endDate = now.plusDays(3);

        WebElement startDateInput = driver.findElement(By.xpath("//input[@formcontrolname='startDateInput']"));
        WebElement endDateInput = driver.findElement(By.xpath("//input[@formcontrolname='endDateInput']"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        String startDateText = startDate.format(formatter);
        String endDateText = endDate.format(formatter);

        startDateInput.click();
        startDateInput.sendKeys(startDateText);
        endDateInput.click();
        endDateInput.sendKeys(endDateText);
        actions.sendKeys(Keys.ENTER).perform();
        reservationCalendarButton.click();
        applyButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driver -> {
            WebElement priceInput = driver.findElement(By.xpath("//input[@formcontrolname='priceInput']"));
            return !priceInput.getAttribute("value").isEmpty();
        });

        WebElement priceInput = driver.findElement(By.xpath("//input[@formcontrolname='priceInput']"));
        int price = Integer.parseInt(priceInput.getAttribute("value"));

        System.out.println(price);
        if(price!=0){
            WebDriverWait waitSnackbar = new WebDriverWait(driver, Duration.ofSeconds(40));
            waitSnackbar.until(ExpectedConditions.invisibilityOfElementLocated(By.className("mat-mdc-snack-bar-label")));
            buttonReserve.click();
        }
    }

    public void createTwoReservationRequestsThatAreOverlapping() {
        Actions actions = new Actions(driver);
        actions.moveToElement(buttonReserve);
        actions.perform();

        numberOfGuests.click();
        actions.sendKeys(Keys.ENTER).perform();

        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusDays(4);
        LocalDate endDate = now.plusDays(6);

        LocalDate startDate1 = now.plusDays(5);
        LocalDate endDate1 = now.plusDays(7);

        WebElement startDateInput = driver.findElement(By.xpath("//input[@formcontrolname='startDateInput']"));
        WebElement endDateInput = driver.findElement(By.xpath("//input[@formcontrolname='endDateInput']"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        String startDateText = startDate.format(formatter);
        String endDateText = endDate.format(formatter);

        String startDateText1 = startDate1.format(formatter);
        String endDateText1 = endDate1.format(formatter);

        startDateInput.click();
        startDateInput.sendKeys(startDateText);
        endDateInput.click();
        endDateInput.sendKeys(endDateText);
        actions.sendKeys(Keys.ENTER).perform();
        reservationCalendarButton.click();
        applyButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driver -> {
            WebElement priceInput = driver.findElement(By.xpath("//input[@formcontrolname='priceInput']"));
            return !priceInput.getAttribute("value").isEmpty();
        });

        WebElement priceInput = driver.findElement(By.xpath("//input[@formcontrolname='priceInput']"));
        int price = Integer.parseInt(priceInput.getAttribute("value"));

        System.out.println(price);
        if(price!=0){
            WebDriverWait waitSnackbar = new WebDriverWait(driver, Duration.ofSeconds(15));
            waitSnackbar.until(ExpectedConditions.invisibilityOfElementLocated(By.className("mat-mdc-snack-bar-label")));
            actions.moveToElement(buttonReserve).build().perform();
            buttonReserve.click();
        }



        startDateInput.click();
        startDateInput.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        endDateInput.click();
        endDateInput.sendKeys(Keys.CONTROL + "a", Keys.DELETE);



        startDateInput.click();
        startDateInput.sendKeys(startDateText1);
        endDateInput.click();
        endDateInput.sendKeys(endDateText1);
        actions.sendKeys(Keys.ENTER).perform();
        reservationCalendarButton.click();
        applyButton.click();

        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait1.until(driver -> {
            WebElement priceInput1 = driver.findElement(By.xpath("//input[@formcontrolname='priceInput']"));
            return !priceInput1.getAttribute("value").isEmpty();
        });

        WebElement priceInput1 = driver.findElement(By.xpath("//input[@formcontrolname='priceInput']"));
        int price1 = Integer.parseInt(priceInput1.getAttribute("value"));

        System.out.println(price1);
        if(price1!=0){
            WebDriverWait waitSnackbar = new WebDriverWait(driver, Duration.ofSeconds(30));
            waitSnackbar.until(ExpectedConditions.invisibilityOfElementLocated(By.className("mat-mdc-snack-bar-label")));
            actions.moveToElement(buttonReserve).build().perform();
            buttonReserve.click();
        }
    }
}
