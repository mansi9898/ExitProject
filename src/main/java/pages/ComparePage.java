package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ComparePage {
	private WebDriver driver;

    // Constructor to initialize the WebDriver
    public ComparePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void clickCompareButton() {
    	WebElement checkBox = driver.findElement(By.className("XqNaEv"));
    	checkBox.click();
    }
    public void goToComparePage() {
    	WebElement compareButton = driver.findElement(By.partialLinkText("COMPARE"));
    	compareButton.click();
    }
}
