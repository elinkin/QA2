package sportdirectsearch;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * This is the test for Sportdirect search
 */
public class SportdirectSearchTest {

    @Test
    public void sportdirectsearch() {
        System.setProperty("webdriver.gecko.driver", "/Users/elinkin/Downloads/QA course/geckodriver");
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://lv.sportsdirect.com//");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement searchresult = wait.until(ExpectedConditions.elementToBeClickable(By.id("advertPopup")));
        //WebElement close = driver.findElement(By.id("advertPopup"));
        WebElement close = driver.findElement(By.xpath("//button[@class='close']"));
        //searchresult.submit();
        close.submit();

        //WebDriverWait wait = new WebDriverWait(driver, 10);
        //WebElement searchresult = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("JavaGuru - Software Development Courses")));
        //Assert.assertTrue(searchresult.getText().contains("JavaGuru"));
        //driver.quit();
    }
}