//package pages;
//
//import java.time.Duration;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//public class NavigationPage {
//    private WebDriver driver;
//    private WebDriverWait wait;
//
//    // Constructor to initialize the WebDriver and WebDriverWait
//    public NavigationPage(WebDriver driver) {
//        this.driver = driver;
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Wait up to 10 seconds
//        PageFactory.initElements(driver, this);
//    }
//
//    public void selectHomeandFurniture() {
//        WebElement dropDown = driver.findElement(By.cssSelector("div._1ch8e_"));
//
//        // Wait until the element is clickable
//        wait.until(ExpectedConditions.elementToBeClickable(dropDown));
//        
//        // Scroll the element into view if it's not fully visible
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropDown);
//
//        dropDown.click();
//    }
//
//    public void goToFurnitureStudio() {
//        WebElement furnitureLink = driver.findElement(By.partialLinkText("Men's T-Shirts"));
//
//        // Wait until the element is clickable
//        wait.until(ExpectedConditions.elementToBeClickable(furnitureLink));
//        
//        // Scroll the element into view if it's not fully visible
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", furnitureLink);
//
//        furnitureLink.click();
//    }
//}

















package pages;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NavigationPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor to initialize the WebDriver and WebDriverWait
    public NavigationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Wait up to 15 seconds
        PageFactory.initElements(driver, this);
    }
    
    
    public void selectHomeandFurniture() {
        // Locate and click on the furniture image link
        WebElement furnitureImage = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@class='_2puWtW _3a3qyb' and @alt='Furniture']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", furnitureImage);
        furnitureImage.click();

        // Scroll to the bottom of the page
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        
        // Scroll back to the top of the page
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    public void goToOfferZone() {
        // Locate and click on the offer zone link
        WebElement offerZone = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='TSD49J' and @href='/offers-list/top-deals?screen=dynamic&pk=themeViews%3DDT-OMU-A2%3ADT-OMU~widgetType%3DdealCard~contentType%3Dneo&otracker=nmenu_offer-zone']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", offerZone);
        offerZone.click();

        // Scroll to the bottom of the page
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        
        // Scroll back to the top of the page
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }
}