package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class SearchFlightPage {
	private WebDriver driver;

    // Constructor to initialize the WebDriver
    public SearchFlightPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void clickTravelLink() {
    	WebElement groceryLink = driver.findElement(By.partialLinkText("Travel"));
    	groceryLink.click();
    }
    public void enterFromCity() {
    	WebElement cityBox = driver.findElement(By.xpath("//input[@class='v2VFa- rLGgLC g9KxuM smJZop ZjUTQC z2D4XG']"));
    	cityBox.click();
    	// Scroll to the first city option after clicking
    	List<WebElement> options = driver.findElements(By.xpath("//input[@class='v2VFa- rLGgLC g9KxuM smJZop ZjUTQC z2D4XG']/following-sibling::ul/li"));
        for (WebElement option : options) {
            if (option.getText().trim().equals("Mumbai, BOM - Chatrapati Shivaji Airport, India")) {
                option.click();
                break;
            }
        }
    }
    public void enterToCity() {
    	WebElement cityBox = driver.findElement(By.xpath("//input[@class='v2VFa- rLGgLC g9KxuM smJZop G+rzy6 z2D4XG']"));
    	cityBox.click();
    	List<WebElement> options = driver.findElements(By.xpath("//input[@class='v2VFa- rLGgLC g9KxuM smJZop G+rzy6 z2D4XG']/following-sibling::ul/li"));
        for (WebElement option : options) {
            if (option.getText().trim().equals("Gorakhpur, GOP - Gorakhpur, India")) {
                option.click();
                break;
            }
        }
    }
    public void enterReturnDate() {
    	WebElement dateBox = driver.findElement(By.xpath("//input[@class='v2VFa- k2khBy rOnAcM z2D4XG']"));
    	dateBox.click();
    	WebElement date = driver.findElement(By.xpath("//button[@class='pl8ttv']"));
    	Actions actions = new Actions(driver);
   	 	actions.moveToElement(date).click().perform();
    }
    public void clickSearchButton() {
    	WebElement searchButton = driver.findElement(By.xpath("//button[@class='QqFHMw sgUmTV M5XAsp']"));
    	searchButton.click();
    }
    
}

