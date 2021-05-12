package genericlibs;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
// contains the generic utility functions which can be used at test case level or POM level
public class FrameworkUtil implements IAutoConstants {
	
	/**
	 * This method is used to take the "Web Page" Screenshot
	 * @param driver
	 * @param testCaseName
	 * @returns String which contains absolute Screenshot Path
	 */
	public static String getSelfie(WebDriver driver, String testCaseName) {
		String timeStamp = LocalDateTime.now().toString().replace(':','-');
		String filePath = SELFIE_PATH + timeStamp + testCaseName + ".png";
		
		TakesScreenshot ts = (TakesScreenshot)driver;
		File tempFile=ts.getScreenshotAs(OutputType.FILE);
		File destFile=new File(filePath);
		
		try {
			FileUtils.copyFile(tempFile, destFile);
			return destFile.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Takes the "WebElement Screenshot"
	 * @param targetElement
	 * @param elementName like loginButton, usernameTextField etc..
	 * @returns String which contains absolute Screenshot Path
	 */
	public static String getSelfie(WebElement targetElement, String elementName) {
		String timeStamp = LocalDateTime.now().toString().replace(':','-');
		String filePath = SELFIE_PATH + timeStamp + elementName + ".png";
		
		File tempFile=targetElement.getScreenshotAs(OutputType.FILE);
		File destFile=new File(filePath);
		
		try {
			FileUtils.copyFile(tempFile, destFile);
			return destFile.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * waits for given seconds of time
	 * @param seconds 
	 */
	public static void sleepInSeconds(long seconds) {
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}