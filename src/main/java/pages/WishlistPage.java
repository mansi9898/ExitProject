package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WishlistPage {
	private WebDriver driver;

    // Constructor to initialize the WebDriver
    public WishlistPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
	public void clickHeartButton() {
		WebElement heart = driver.findElement(By.xpath("//div[@class='oUss6M ssUU08']"));
		heart.click();
	}
}
