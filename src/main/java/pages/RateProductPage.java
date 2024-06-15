package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class RateProductPage {
	private WebDriver driver;

    // Constructor to initialize the WebDriver
    public RateProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void clickRating() {
        WebElement rating = driver.findElement(By.xpath("//div[@class='XQDdHH']"));
        rating.click();
    }
    public void clickRatingButton() {
    	 WebElement ratingButton = driver.findElement(By.xpath("//button[@class='QqFHMw YEbu1k']"));
         ratingButton.click();
    }
}
