package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class HomePage {
    private WebDriver driver;
    private By flightsLink = new By.ByXPath("//a[@data-decider-header = 'flights']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public SearchFlightsPage selectFlightsTab() {
        driver.findElement(flightsLink).click();
        return new SearchFlightsPage(driver);
    }


}
