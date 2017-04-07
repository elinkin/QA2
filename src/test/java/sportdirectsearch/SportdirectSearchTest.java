package sportdirectsearch;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
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
        System.setProperty("webdriver.gecko.driver", "//Users/elinajudelovica/Downloads/geckodriver");
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://lv.sportsdirect.com//");
        ((JavascriptExecutor) driver).executeScript( "window.close()" );
        //Alert alert = driver.switchTo().alert();
        //alert.dismiss();
        //WebElement close = driver.findElement(By.className("close"));
        //WebElement close = driver.findElement(By.xpath("//[button[@class='close']"));
        //WebDriver close = driver.findElement(By.xpath("//div[contains(@class='close']"));
        //close.click();
        //element.submit();
        //WebDriverWait wait = new WebDriverWait(driver, 10);
        //WebElement searchresult = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("JavaGuru - Software Development Courses")));
        //Assert.assertTrue(searchresult.getText().contains("JavaGuru"));
        //driver.quit();
    }
}