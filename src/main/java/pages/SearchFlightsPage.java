package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;


public class SearchFlightsPage {

    private WebDriver driver;
    private By oneWayOption = new By.ByXPath("//label[contains(@id,'oneway-label')]/span");
    private By originInput = new By.ByXPath("//input[@placeholder='From where?']");
    private By destinationInput = new By.ByXPath("//input[@placeholder='To where?']");
    private By departureDate = new By.ByXPath("//div[contains(@id,'depart-input')]");
    private By returnDate = new By.ByXPath("//div[contains(@id,'return-input')]");
    private By searchButton = new By.ByXPath("//button[@title = 'Search flights']");

    public SearchFlightsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectOneWayFlight() {
        driver.findElement(oneWayOption).click();

        FluentWait wait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(returnDate));
    }

    public void enterOrigin(String origin) {
        WebElement originElement = driver.findElement(originInput);

        originElement.click();
        originElement.sendKeys(origin);
        originElement.sendKeys(Keys.ENTER);
    }

    public void enterDestination(String destination) {
        WebElement destinationElement = driver.findElement(destinationInput);
        destinationElement.click();
        destinationElement.sendKeys(destination);
        destinationElement.sendKeys(Keys.ENTER);
    }

    public void enterDepartureDate(LocalDate date) {
        String dateInput = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        WebElement departureDateInput = driver.findElement(departureDate);

        Actions builder = new Actions(driver);

        Action typeDate = builder.sendKeys(departureDateInput, dateInput).sendKeys(Keys.ENTER).build();
        typeDate.perform();
    }

    public FlightsResultsPage search() {
        driver.findElement(searchButton).click();
        return new FlightsResultsPage(driver);
    }
}
