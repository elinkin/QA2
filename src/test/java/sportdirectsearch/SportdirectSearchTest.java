package sportdirectsearch;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
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
        WebElement sports = driver.findElement(By.xpath("//*[@id='topMenu']/ul/li[6]/a"));
        sports.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement popup = wait.until(ExpectedConditions.elementToBeClickable(By.id("advertPopup")));
        WebElement close = driver.findElement(By.xpath("//*[@id='advertPopup']/div/div/div[1]/button[@class='close']"));
        close.click();
        WebElement cookies = driver.findElement(By.id("inputAcceptCookies"));
        cookies.click();
        //WebElement swimming = driver.findElement(By.xpath("//*[@id='dnn_ctr29808065_HtmlModule_lblContent']/div/div/div[2]/div[1]/ul/li[32]"));
        WebElement swimming = driver.findElement(By.xpath("//img[@src='/images/marketing/sports-aw16-image-4b.jpg']"));
        swimming.click();
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        //WebElement swimsuits = driver.findElement(By.xpath("//a[@href='/swimming/ladies-swimwear/swimsuits']"));
        //WebElement swimsuits = driver.findElement(By.xpath("//*[@id='module13-img4']/a[@href='/swimming/ladies-swimwear']"));
        List<WebElement> swimsuits = driver.findElements(By.xpath("//*[@id='dnn_ctr29808104_HtmlModule_lblContent']/div[2]/div[1]/div/div/div[3]/div[1]/div/div/nav/ul"));
        //swimsuits.click();
        for(WebElement e : swimsuits) {
            System.out.println(e);
        }
        System.out.println("got to this line");


        //Actions action = new Actions(driver);
        //action.moveToElement(sports).moveToElement(driver.findElement(By.xpath("//*[@id='topMenu']/ul/li[6]/div/ul/li[2]/ul/li[5]/ul/li[1]/a")));
        //action.click();
        //Assert.assertTrue(hovermenu.getText().contains("Swimming"));
        //driver.quit();
    }
}