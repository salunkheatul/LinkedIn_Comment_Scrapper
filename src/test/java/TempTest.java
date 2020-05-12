

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class TempTest {
    private WebDriver driver;


    @Test()
    public void firstTest() throws NoSuchElementException, InterruptedException {
        String chromeBrowserPath = "/Atul/Selenium_TestNG_Log4j_ExtentReport_Listeners/temp/src/main/resources/chromedriver";
        System.setProperty("webdriver.chrome.driver", chromeBrowserPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-fullscreen");
        options.setHeadless(false);
        driver = new ChromeDriver(options);

        driver.get("https://www.linkedin.com/login?fromSignIn=true&trk=guest_homepage-basic_nav-header-signin");
        driver.findElement(By.id("username")).sendKeys("epictestbuster@gmail.com");
        driver.findElement(By.id("password")).sendKeys("grey@123");
        driver.findElement(By.cssSelector("div#app__container button[type=\"submit\"]")).click();

        driver.get("https://uat-commit.live/success-stories");
        for (int i = 1; i <= 17; i++) {
            WebElement element = driver.findElement(By.cssSelector("div.cd-section.section-light-gray.pt-4.pb-4 > div.container > div:nth-child(2) > div"));
            WebElement element1 = driver.findElement(By.cssSelector("[onclick='renderSuccessStories\\(\\)']"));
            Actions actions = new Actions(driver);
            actions.moveToElement(element1).sendKeys(Keys.DOWN);
            actions.click();
            actions.perform();
        }
        List<WebElement> list = driver.findElement(By.cssSelector(".col-md-12 > .other-success-stories.row")).findElements(By.cssSelector(".col-md-12 > .other-success-stories.row > .col-md-4.d-flex"));
        int j = 1;
        String href = null;
        String linkedInName = null;
        for (WebElement element3 : list) {
            String name = element3.findElement(By.cssSelector(".col-md-4.d-flex > div > .bg-azure-light > div > .col-md-8 > h4")).getText();
            try {
                href = element3.findElement(By.cssSelector(".col-md-4.d-flex > div > .bg-azure-light > div > .col-md-8 > a")).getAttribute("href");
            } catch (Exception e) {
                System.out.println("not present");
            }
            //((JavascriptExecutor)driver).executeScript("window.open('"+href+"','_blank');");
            driver.findElement(By.cssSelector("Body")).sendKeys(Keys.CONTROL + "t");
            driver.get(href);

            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            Thread.sleep(1000);
            try {
                linkedInName = driver.findElement(By.cssSelector("div#profile-wrapper li.inline.t-24.t-black.t-normal.break-words")).getText();
            } catch (Exception e) {
                System.out.println("not present");
            }
            if (linkedInName != null && linkedInName.contains(name)) {
                System.out.println("Profile Match / Valid link");
            }
            Thread.sleep(500);

            driver.switchTo().window(tabs.get(0));
            j++;
        }
    }

    @AfterTest
    public void tearDown() {
        driver.close();
    }

}
