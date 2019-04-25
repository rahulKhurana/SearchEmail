package pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.model.Log;

import utils.Constants;
import utils.SeleniumUtils;

public class HomePage extends Constants {

	WebDriver driver;
	ExtentTest exTest;

	public HomePage(WebDriver driver,ExtentTest exTest) {
		this.driver = driver;
		this.exTest = exTest;
	}

	By search = By.xpath("//input[@aria-label='Search mail']");
	By emailList = By.xpath("//div[@class='BltHke nH oy8Mbf']/descendant::tbody/tr");
	By attachment = By.xpath("//span/a[@data-tooltip-class='a1V']");
	By organizeInDrive = By.xpath("//div[@data-tooltip='Organize in Drive']");
	By addToDrive = By.xpath("//div[@data-tooltip='Save to Drive']");
	By searchGlassIcon = By.xpath("//button[@aria-label='Search Mail']");

	By looksSafe = By.xpath("//button[text()='Looks safe']");
	By messageMarked = By.xpath("//span[contains(text(),'Message marked')]");

	By gDriveGlobalLeftCornerClose = By.xpath("//div[@class='bBe']");
	By gDriveError = By.xpath("//span[contains(text(),'There was an error saving')]");
	By infoClose = By.xpath("//div[@title='Close']");
	By gDriveSuccess = By.xpath("//span[text()='The attachment has been saved to Google Drive.']");

	public void enterTextforSearchEmails(String text) throws InterruptedException {
		exTest.info("entering search text :"+ text);
		//		text ="citibank account statement with has:attachment";
		SeleniumUtils.getWebElement(driver,search).sendKeys(text);
		SeleniumUtils.getWebElement(driver, searchGlassIcon).click();
	}

	public void addSearchedEmailsToGoogleDrive() throws InterruptedException, IOException {

		List<WebElement> emailsList = SeleniumUtils.getWebElements(driver, emailList);

		int size = emailsList.size();

		for(int i=1;i<=size;i++) {
			try {
				WebElement email = driver.findElement(By.xpath("//div[@class='BltHke nH oy8Mbf']/descendant::tbody/tr["+i+"]"));
				email.click();
			}catch(Exception e) {;
			exTest.warning("Unable to click email row tr. Now trying to click specific table data.");
			WebElement emailTD = driver.findElement(By.xpath("//div[@class='BltHke nH oy8Mbf']/descendant::tbody/tr["+i+"]/td[6]"));
			emailTD.click();
			}

			//handle looks safe button and subsequent message 
			/*if(SeleniumUtils.validateElementPresentAndClick(driver, looksSafe)) {
				if(SeleniumUtils.validateElementPresent(driver, messageMarked))
					SeleniumUtils.getWebElement(driver, gDriveGlobalLeftCornerClose).click();
			}*/
			
			//fetch the email attachment and scroll/move to the same
			WebElement emailAttachment = SeleniumUtils.getWebElement(driver,attachment);
			if(BROWSER.equals("firefox")) {
				SeleniumUtils.scrollToView(driver, emailAttachment);
			}else {
				//this code works only in chrome
				SeleniumUtils.moveToWebElement(emailAttachment, driver);
			}
			exTest.info("clicking the attachment add to drive button");
			String fileName = emailAttachment.getText();
			exTest.info("Attachment name :"+fileName);

			if(SeleniumUtils.validateElementPresent(driver, organizeInDrive)) {
				exTest.pass("File already present in gDrive. No need to add again.");
				exTest.addScreenCaptureFromPath(SeleniumUtils.takeScreenshot(fileName, driver));
			}else
			{
				SeleniumUtils.getWebElement(driver,addToDrive).click();
				for(int j=1;j<5;j++) {
					if(SeleniumUtils.validateElementPresent(driver, gDriveError)) {
						exTest.addScreenCaptureFromPath(SeleniumUtils.takeScreenshot(fileName, driver));
						//first click close of Unable to add to My Drive button
						SeleniumUtils.validateElementPresentAndClick(driver,infoClose);
						//then close the "there was an error"
						SeleniumUtils.validateElementPresentAndClick(driver,gDriveGlobalLeftCornerClose);
						break;
					}else if(SeleniumUtils.validateElementPresent(driver, gDriveSuccess)){
						exTest.addScreenCaptureFromPath(SeleniumUtils.takeScreenshot(fileName, driver));
						SeleniumUtils.validateElementPresentAndClick(driver,infoClose);
						SeleniumUtils.validateElementPresentAndClick(driver,gDriveGlobalLeftCornerClose);
						break;
					}else {
						exTest.info("Waiting for 1 second");
						Thread.sleep(1000);
					}
				}
			}
			exTest.info("navigating back");
			driver.navigate().back();
		}
		exTest.info("All emails clicked upon :"+size);
	}


}
