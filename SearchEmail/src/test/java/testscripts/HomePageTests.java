package testscripts;

import java.io.IOException;

import org.testng.annotations.Test;

import pages.HomePage;
import pages.SignInPage;

public class HomePageTests extends ConfigTest {

	@Test
	public void searchAndDownload() throws InterruptedException, IOException {
		exTest = extent.createTest("searchAndDownload");
		
		SignInPage spage = new SignInPage(driver,exTest);
		spage.getURL("https://gmail.com");
		spage.enterEmailId(EMAILID);
		spage.clickNext();
		spage.enterPassword(PASSWORD);
		spage.clickNext();
		HomePage hpage = new HomePage(driver,exTest);
		hpage.enterTextforSearchEmails(TEXTTOSEARCH);
		hpage.addSearchedEmailsToGoogleDrive();
	}
}
