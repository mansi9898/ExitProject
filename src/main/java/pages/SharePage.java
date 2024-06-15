package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SharePage {
	private WebDriver driver;

    // Constructor to initialize the WebDriver
    public SharePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void clickShareButton() {
    	WebElement shareButton = driver.findElement(By.xpath("//div[@class='EjOX7q']"));
    	shareButton.click();
    }
    public void selectEmail() {
    	WebElement emailButton = driver.findElement(By.xpath("//img[@class='R8VLb7']"));
    	emailButton.click();
    }
}
