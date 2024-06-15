package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {
	private WebDriver driver;

    // Constructor to initialize the WebDriver
    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void searchProduct(String productName) {
    	WebElement searchInput = driver.findElement(By.className("Pke_EE"));
        searchInput.sendKeys(productName);
        // Press Enter to perform the search
        searchInput.sendKeys(Keys.ENTER);
    }
}

