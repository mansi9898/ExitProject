package pages;

import java.time.Duration;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class GroceryPage {
	private WebDriver driver;
	
    // Constructor to initialize the WebDriver
    public GroceryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
   
    public void clickGroceryLink() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));// Set the timeout to 10 seconds
        WebElement groceryLink = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Grocery")));
        try {
            groceryLink.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            // Use JavaScript to click the element if it's intercepted
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", groceryLink);
        }
    }

    public void enterPincode(String pin) {
    	WebElement pincode = driver.findElement(By.name("pincode"));
    	pincode.sendKeys(pin);
    	pincode.sendKeys(Keys.ENTER);
    }
}
