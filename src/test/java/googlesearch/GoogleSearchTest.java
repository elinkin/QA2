package googlesearch;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * This is the first test for QA2
 */
public class GoogleSearchTest {

    @Test
    public void firstTest() throws InterruptedException {
        System.setProperty("webdriver.gecko.driver", "/Users/elinkin/Downloads/QA course/geckodriver");
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.google.lv/");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("javaguru");
        element.submit();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement searchresult = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("JavaGuru - Software Development Courses")));
        Assert.assertTrue(searchresult.getText().contains("JavaGuru"));
        driver.quit();
    }
}