package keywords;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import reports.ExtentManager;

public class GenericKeywords {
	public WebDriver driver;
	public ExtentTest test;
	public Properties prop;
	public SoftAssert softAssert;

	public void openBrowser(String name) {
		log("Opening Browser: "+name);

		if(name.equals("Chrome")) {
			System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");

			ChromeOptions options = new ChromeOptions();
			options.setPageLoadStrategy(PageLoadStrategy.EAGER);
			options.addArguments("--disable-notifications"); //Handles Notifications
			options.addArguments("--start-maximized"); //Opens the browser maximized
			options.addArguments("ignore-certificate-errors"); //Handles Certificate errors

			driver = new ChromeDriver(options);
		}
		else if(name.equals("Mozilla")) {
			FirefoxOptions op = new FirefoxOptions();
			op.setPageLoadStrategy(PageLoadStrategy.EAGER);
			FirefoxProfile prof = new FirefoxProfile(); //allProf.getProfile(""); //Can get your profile of choice
			prof.setPreference("dom.webnotifications.enabled", false); //Manage notifications
			
			op.setProfile(prof);
			driver = new FirefoxDriver(op);
		}
		else if(name.equals("Edge")) {
			System.setProperty(EdgeDriverService.EDGE_DRIVER_SILENT_OUTPUT_PROPERTY, "true");

			EdgeOptions options = new EdgeOptions();
			options.setPageLoadStrategy(PageLoadStrategy.EAGER);
			options.addArguments("--disable-notifications"); //Handles Notifications
			options.addArguments("--start-maximized"); //Opens the browser maximized
			//options.addArguments("ignore-certificate-errors"); //Handles Certificate errors

			driver = new EdgeDriver(options);
		}

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	public void navigate(String url) {
		log("Navigating to URL: "+prop.getProperty(url));
		driver.get(prop.getProperty(url));
	}
	
	public void click(String locatorKey) {
		String locator = prop.getProperty(locatorKey);
		log("Clicking on the locator: "+locator);
		driver.findElement(getLocator(locatorKey)).click();
	}
	
	public void type(String locatorKey, String text) {
		String locator = prop.getProperty(locatorKey);
		log("Entering text: "+text+" into locator: "+locator);
		driver.findElement(getLocator(locatorKey)).sendKeys(text);
	}
	
	public void enter(String locatorKey) {
		String locator = prop.getProperty(locatorKey);
		log("Pressing ENTER button for locator: "+locator);
		driver.findElement(getLocator(locatorKey)).sendKeys(Keys.RETURN);
	}
	
	public String getValue(String locatorKey) {
		String locator = prop.getProperty(locatorKey);
		log("Obtaining the data from locator: "+locator);

		return driver.findElement(getLocator(locatorKey)).getText();
	}

	public By getLocator(String el) {
		By by = null;
		if(el.endsWith("_id")){
			by = By.id(prop.getProperty(el));
		}
		else if(el.endsWith("_class")) {
			by = By.className(prop.getProperty(el));
		}
		else if(el.endsWith("_xpath")) {
			by = By.xpath(prop.getProperty(el));
		}
		else if(el.endsWith("_name")) {
			by = By.name(prop.getProperty(el));
		}

		return by;
	}

	public void log(String text) {
		System.out.println(text);
		test.log(Status.INFO, text);
	}

	public void reportFailure(String text, boolean stopTest) {
		test.log(Status.FAIL, text);
		softAssert.fail(text);
		takeScreenshot();
		
		if(stopTest) {
			Reporter.getCurrentTestResult().getTestContext().setAttribute("criticalFailure", "yes");
			assertAll();
		}
	}

	public void assertAll() {
		softAssert.assertAll();
	}

	public void takeScreenshot() {
		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_").replace(" ", "_")+".png";
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//System.out.println(ExtentManager.screenshotFolderPath);
		//System.out.println(screenshotFile);
		try {
			FileUtils.copyFile(src, new File(ExtentManager.screenshotFolderPath+screenshotFile));
			//test.addScreenCaptureFromPath("path of image", "");
			test.log(Status.INFO, "Screenshot -> "+test.addScreenCaptureFromPath(ExtentManager.screenshotFolderPath+screenshotFile));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void waitForPageToLoad(){
		JavascriptExecutor js = (JavascriptExecutor) driver;
		int i=0;
		
		while(i!=10){
		String state = (String)js.executeScript("return document.readyState;");
		System.out.println(state);

		if(state.equals("complete"))
			break;
		else
			wait(2);

		i++;
		}
		/*
		// check for jquery status
		i=0;
		while(i!=10){
	
			Long d= (Long) js.executeScript("return jQuery.active;");
			System.out.println(d);
			if(d.longValue() == 0 )
			 	break;
			else
				 wait(2);
			 i++;
				
			}
		*/
	}
	
	public void wait(int time) {
		try {
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
