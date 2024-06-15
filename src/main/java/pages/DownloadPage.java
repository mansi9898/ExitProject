//package pages;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.PageFactory;
//
//public class DownloadPage {
//	private WebDriver driver;
//
//    // Constructor to initialize the WebDriver
//    public DownloadPage(WebDriver driver) {
//        this.driver = driver;
//        PageFactory.initElements(driver, this);
//    }
//    public void clickSellerLink() {
//    	WebElement sellerLink = driver.findElement(By.partialLinkText("Become a Seller"));
//    	sellerLink.click();
//    }
//    public void clickShopsyLink() {
//    	WebElement shopsyButton = driver.findElement(By.partialLinkText("Shopsy"));
//    	shopsyButton.click();
//    }
//    public void downloadKit() {
//    	WebElement downloadButton = driver.findElement(By.xpath("//button[@class='styles__ButtonStyle-sekd9q-0 klTPPh styles__StyledButton-sc-wrjoks-6 fZoYTS']"));
//    	Actions actions = new Actions(driver);
//   	 	actions.moveToElement(downloadButton).click().perform();
//    }
//    public void goToTop() {
//    	WebElement arrow = driver.findElement(By.cssSelector("div[class='styles__GoTop-sc-1xt26qw-0 gqDWSG']"));
//    	arrow.click();
//    }
//}




package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DownloadPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor to initialize the WebDriver and WebDriverWait
    public DownloadPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // 20 seconds wait time
        PageFactory.initElements(driver, this);
    }

    public void clickSellerLink() {
        WebElement sellerLink = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Become a Seller")));
        sellerLink.click();
    }

    public void clickShopsyLink() {
        WebElement shopsyButton = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Shopsy")));
        shopsyButton.click();
    }

    public void downloadKit() {
    	// Wait for the "Download Launch Kit" button to be clickable
        WebElement downloadButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'styles__ButtonStyle') and text()='Download Launch Kit']")));
        
        // Scroll to the "Download Launch Kit" button
        Actions actions = new Actions(driver);
        actions.moveToElement(downloadButton).perform();
        
        // Click on the "Download Launch Kit" button
        WebElement downloadButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'styles__ButtonStyle') and text()='Download Launch Kit']")));
        downloadButton1.click();
    }

    public void goToTop() {
    	JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0)");
    }
}
