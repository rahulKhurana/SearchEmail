package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.TakesScreenshot;

public class SeleniumUtils extends Constants {

	public static void highlighter(WebDriver driver, WebElement element) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String var = (String) js.executeScript("return arguments[0].getAttribute('style', arguments[1]);", element); 
		js.executeScript("return arguments[0].setAttribute('style', arguments[1]);", element, "border: 4px solid red; ");
		Thread.sleep(50);
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, var);
	}

	public static void highlighter(WebDriver driver, List<WebElement> list) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		for(WebElement element : list){
			String var = (String) js.executeScript("return arguments[0].getAttribute('style', arguments[1]);", element); 
			js.executeScript("return arguments[0].setAttribute('style', arguments[1]);", element, "border: 4px solid red; ");
			Thread.sleep(50);
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, var);
		}
	}

	public static void moveToWebElement(WebElement ele,WebDriver driver) {
		Actions action = new Actions(driver);
		action.moveToElement(ele);
		action.perform();
	}

	public static void waitForElement(WebDriver driver,By by) {
		WebDriverWait wait = new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}
	
	public static void scrollDownToTheBottom(WebDriver driver) {
		 JavascriptExecutor js = (JavascriptExecutor) driver;
	     js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}
	
	public static void scrollToView(WebDriver driver,WebElement element) {
		((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView();", element);
	}
	
	public static void scrollToElementPos(WebDriver driver,WebElement element) {
		int y = element.getLocation().getY();
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0,"+y+")");
	}
	
	

	public static WebElement getWebElement(WebDriver driver,By by) throws InterruptedException {
		waitForElement(driver,by);
		WebElement ele = driver.findElement(by);
		highlighter(driver, ele);
		return ele;
	}

	public static List<WebElement> getWebElements(WebDriver driver,By by) {
		waitForElement(driver,by);
		return driver.findElements(by);
	}
	
	public static boolean validateElementPresentAndClick(WebDriver driver,By by) throws InterruptedException {
		boolean flag = false;
		List<WebElement> list = driver.findElements(by);
		if(list.size()>0) {
			getWebElement(driver, by).click();
			flag = true;
			}
		return flag;
	}
	

	public static boolean validateElementPresent(WebDriver driver,By by) throws InterruptedException {
		boolean flag = false;
		List<WebElement> list = driver.findElements(by);
		if(list.size()>0)
			flag = true;
		return flag;
	}
	
	public static Properties loadConfigFile( String fileName) throws IOException {
		FileInputStream fs = null;
		Properties prop = new Properties();

		String folderName = System.getProperty("user.dir") + "/config";

		fs = new FileInputStream(folderName+ "/"+fileName+".properties");
		prop.load(fs);
		return prop;

	}
	
	public static String takeScreenshot(String name,WebDriver driver) throws IOException{
		File scrFile = ((org.openqa.selenium.TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String arr[] = name.split(".pdf");
		name = arr[0].substring(arr[0].indexOf("_")+1, arr[0].lastIndexOf("_"));
		name = name.substring(name.indexOf("_")+1);
		String timestamp = new SimpleDateFormat("HH.mm.ss_MM.dd.yyyy").format(Calendar.getInstance().getTime()); 
		name = System.getProperty("user.dir") + SCREENSHOTSPATH +name+"_"+timestamp+".png";
		File destFile=new File(name);

        FileUtils.moveFile(scrFile, destFile);
		return name;
	}

	public static void scrollBy(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		 jse.executeScript("window.scrollBy(0,250)", "");
	}
}
