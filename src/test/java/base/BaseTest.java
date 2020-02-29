package base;

import com.google.common.io.Files;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.internal.TestResult;
import pages.HomePage;
import utils.Consts;
import utils.WindowManager;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BaseTest {
    private WebDriver driver;
    protected HomePage homePage;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("disable-infobars");

        driver = new RemoteWebDriver(
                new URL(Consts.SeleniumHub),
                options);

        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();

        homePage = new HomePage(driver);

    }

    @BeforeMethod
    public void goHome(){
        driver.get(Consts.TestUrl);
    }

    @AfterMethod
    public void recordFailure(ITestResult result){

        if (result.getStatus() == TestResult.FAILURE) {
            TakesScreenshot camera = (TakesScreenshot) driver;
            File screenShot = camera.getScreenshotAs(OutputType.FILE);
            System.out.println("Path to screenshort: " + screenShot.getAbsolutePath());
            try {
                String testName = result.getName();
                String runDate = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
                Files.move(screenShot.getAbsoluteFile(),
                        new File("resources/screenshots/"+runDate+"_"+testName+".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    public WindowManager getWindowManager() {
        return new WindowManager(driver);
    }

}
