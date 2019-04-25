package testscripts;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import utils.Constants;
import utils.SeleniumUtils;

public class ConfigTest extends Constants{

	WebDriver driver;
	ExtentReports extent;
	ExtentTest exTest;

	@BeforeSuite
	public void initReport() throws IOException {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		Properties prop = SeleniumUtils.loadConfigFile("testConfig");

		EMAILID = prop.getProperty("id");
		PASSWORD = prop.getProperty("pwd");
		CHROMEPATH = prop.getProperty("chromedriverpath");
		FIREFOXPATH = prop.getProperty("firefoxdriverpath");
		BROWSER = prop.getProperty("browser");
		TEXTTOSEARCH = prop.getProperty("searchtext");
		SCREENSHOTSPATH = prop.getProperty("screenshotspath");
	}

	@BeforeTest
	public void init() {
		if(BROWSER.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+CHROMEPATH);
			driver = new ChromeDriver();
		}
		else if(BROWSER.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+FIREFOXPATH);
			driver = new FirefoxDriver();
		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@AfterSuite
	public void finishReport() throws IOException {
		Runtime.getRuntime().exec("taskkill /im chromedriver.exe /f /t");
		extent.flush();
//		driver.quit();
		
	}
}
