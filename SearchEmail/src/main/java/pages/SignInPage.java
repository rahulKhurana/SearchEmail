package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;

import utils.SeleniumUtils;

public class SignInPage {

	WebDriver driver;
	ExtentTest exTest;
	
	public SignInPage(WebDriver driver,ExtentTest exTest) {
		this.driver = driver;
		this.exTest = exTest;
	}
	
	By signIn = By.xpath("//input[@id='identifierId']");
	By next = By.xpath("//span[text()='Next']");
	By password = By.xpath("//input[@type='password']");
	
	public void getURL(String url) {
		exTest.info("Fetching URL"+url);
		driver.get(url);
	}
	public void enterEmailId(String emailId) throws InterruptedException {
		exTest.info("Entering email"+emailId);
		SeleniumUtils.getWebElement(driver,signIn).sendKeys(emailId);
	}
	
	public void clickNext() throws InterruptedException {
		exTest.info("Clicking next");
		SeleniumUtils.getWebElement(driver,next).click();
	}
	
	public void enterPassword(String pwd) throws InterruptedException {
		exTest.info("Entering pwd"+pwd);
		SeleniumUtils.getWebElement(driver,password).sendKeys(pwd);
	}
	
}
