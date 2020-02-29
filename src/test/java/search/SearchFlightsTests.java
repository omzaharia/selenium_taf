package search;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SearchFlightsPage;
import utils.Consts;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SearchFlightsTests extends BaseTest {

    @Test
    public void searchOneWayFlightWithDefaultNoOfPersons() {
        String departure = "MUC";
        String destination = "GVA";
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String testDate = tomorrow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //ToDo: In case the URL assertion will be used in several test create a private method for building the expected URL
        String expectedUrl = Consts.FlightsUrl + "/flights/MUC-GVA/" + testDate + "-flexible/1adults/?sort=bestflight_a";

        SearchFlightsPage searchFlightsPage = homePage.selectFlightsTab();
        searchFlightsPage.selectOneWayFlight();
        searchFlightsPage.enterOrigin(departure);
        searchFlightsPage.enterDestination(destination);
        searchFlightsPage.enterDepartureDate(tomorrow);
        searchFlightsPage.search();

        Assert.assertEquals(getWindowManager().getCurrentUrl(), expectedUrl);
    }

}
