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
        WebElement popup = wait.until(ExpectedConditions.elementToBeClickable(By.id("advertPopup")));
        WebElement close = driver.findElement(By.xpath("//*[@id='advertPopup']/div/div/div[1]/button[@class='close']"));
        close.click();
        //WebDriverWait wait1 = new WebDriverWait(driver, 20);
        //WebElement hovermenu = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".s_swimming > a:nth-child(1)")));
        //Actions action = new Actions(driver);
        WebElement sports = driver.findElement(By.xpath("//*[@id='topMenu']/ul/li[6]/a"));
        sports.click();
        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        WebElement popup1 = wait.until(ExpectedConditions.elementToBeClickable(By.id("advertPopup")));
        WebElement close1 = driver.findElement(By.xpath("//*[@id='advertPopup']/div/div/div[1]/button[@class='close']"));
        close1.click();
        WebElement swimming = driver.findElement(By.xpath("//*[@id='dnn_ctr99319_HtmlModule_lblContent']/div/div/div[2]/div[1]/ul/li[32]/a[@href='/swimming']"));
        //WebElement swimming = driver.findElement(By.cssSelector(".menu-margin > li:nth-child(32) > a:nth-child(1)"));
        swimming.click();
        //action.moveToElement(sports).moveToElement(driver.findElement(By.xpath("//[@class='SubMenuWrapper']/ul/li/ul/li[5]/ul/li[@href='/swimming']")));
        //action.click();
        //Assert.assertTrue(hovermenu.getText().contains("Swimming"));
        //driver.quit();
    }
}