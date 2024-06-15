package pages;

import org.openqa.selenium.By;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PincodePage {
	
	private WebDriver driver;

    // Constructor to initialize the WebDriver
    public PincodePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
	@FindBy(id = "pincodeInputId")
    private WebElement pinCodeBar;
	
	public void enterPincode(String pinCode) 
	{
        pinCodeBar.sendKeys(pinCode);
        // Press Enter to perform the search
        pinCodeBar.sendKeys(Keys.ENTER);
    }
}
