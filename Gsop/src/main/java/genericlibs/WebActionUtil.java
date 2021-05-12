package genericlibs;

import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * It is a Wrapper class
 * it contains wrapper methods to perform webactions
 * @author SkillRary
 *
 */
public class WebActionUtil {
	
	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor jse;
	Actions actions;
	
	public WebActionUtil(WebDriver driver, long explict) {
		this.driver=driver;
		wait = new WebDriverWait(driver, explict);
		jse = (JavascriptExecutor)driver;
		actions = new Actions(driver);
	}
	
	/**
	 * A wrapper method to enter data into textfield only after the element
	 * is visible and exiting contents are cleared
	 * @param targetElement make sure its input Element
	 * @param text
	 */
	public void enterData(WebElement targetElement, String text) {
		wait.until(ExpectedConditions.visibilityOf(targetElement));
		if(targetElement.getTagName().equalsIgnoreCase("input")) {
			targetElement.clear();
			targetElement.sendKeys(text);
		} else {
			throw new IllegalArgumentException("Element should be <input>");
		}		
	}
	
	/**
	 * performs click on a webelement
	 * @param targetElement
	 */
	public void click(WebElement targetElement) {
		wait.until(ExpectedConditions.visibilityOf(targetElement));
		wait.until(ExpectedConditions.elementToBeClickable(targetElement));
		try {
			targetElement.click();
		} catch(Exception e1) {
			try {
				System.out.println("Trying to use submit method");
				targetElement.submit();
			} catch(Exception e2) {
				System.out.println("Trying to use Keys.ENTER method");
				targetElement.sendKeys(Keys.ENTER);
			}			
		}
		
	}
	
	/**
	 * As a workaround to click on a WebElement in case of
	 * ElementClickInterceptedException
	 * @param targetElement
	 */
	public void jsClick(WebElement targetElement) {
		wait.until(ExpectedConditions.visibilityOf(targetElement));
		jse.executeScript("arguments[0].click();", targetElement);
	}
	
	/**
	 * Used to perform Veritical scrolling
	 * @param pixels Number of Pixels to Scroll up/Down
	 * @param scrollUp true means scroll Up otherwise scroll Down
	 */
	public void scrollUpOrDown(int pixels, boolean scrollUp) {
		if(scrollUp) {
			jse.executeScript("window.scrollBy(0,-arguments[0]);",pixels);
		} else {
			jse.executeScript("window.scrollBy(0,arguments[0]);",pixels);
		}		
	}
	
	/**
	 * Used to enter data into the textfield if sendKeys() is not working
	 * @param targetElement
	 * @param text
	 */
	public void enterValueUsingJs(WebElement targetElement, String text) {
		jse.executeScript("arguments[0].value='';",targetElement);
		jse.executeScript("arguments[0].value=arguments[1];",targetElement, text);
	}
	
	/**
	 * To perform drag and Drop Mouse Gesture
	 * @param source
	 * @param target
	 */
	public void dragAndDrop(WebElement source, WebElement target) {
		actions.dragAndDrop(source, target).perform();
	}
	
	/**
	 * To perform mouse hovering Gesture
	 * @param targetElement on which mouse hovering to be done
	 */
	public void moveToElement(WebElement targetElement) {
		actions.moveToElement(targetElement).perform();
	}
	
	/**
	 * To perform doubleClick Gesture
	 * @param targetElement on which doubleClick to be done
	 */
	public void doubleClick(WebElement targetElement) {
		actions.doubleClick(targetElement).perform();
	}
	
	/**
	 * To perform rightClick Gesture
	 * @param targetElement on which rightClick to be done
	 */
	public void rightClick(WebElement targetElement) {
		actions.contextClick(targetElement).perform();
	}
	
	/**
	 * Selects the given text in the given listbox
	 * @param targetListBox
	 * @param visibleText
	 */
	public void selectByVisibleText(WebElement targetListBox, String visibleText) {
		Select s = new Select(targetListBox);
		s.selectByVisibleText(visibleText);
	}
	
	/**
	 * Selects the given indexed option in the given listbox
	 * @param targetListBox
	 * @param index (0 Based index)
	 */
	public void selectByIndex(WebElement targetListBox, int index) {
		Select s = new Select(targetListBox);
		s.selectByIndex(index);
	}
	
	/**
	 * deSelects the given text in the given listbox
	 * @param targetListBox
	 * @param visibleText
	 */
	public void deselectByVisibleText(WebElement targetListBox, String visibleText) {
		Select s = new Select(targetListBox);
		if(s.isMultiple()) {
			s.selectByVisibleText(visibleText);
		} else {
			throw new IllegalArgumentException("Given listbox is not MultiSelect Listbox");
		}
		
	}
	
	/**
	 * deSelects all the selected Options in a multiSelect listbox
	 * @param targetListBox
	 */
	public void deselectAll(WebElement targetListBox) {
		Select s = new Select(targetListBox);
		if(s.isMultiple()) {
			s.deselectAll();
		} else {
			throw new IllegalArgumentException("Given listbox is not MultiSelect Listbox");
		}		
	}
	
	/**
	 * Switches to the frame 
	 * @param indexNameOrId provide index, name or Id in String form
	 */
	public void switchToFrame(String indexNameOrId) {
		try {
			int index=Integer.parseInt(indexNameOrId);
			driver.switchTo().frame(index);
		} catch(NumberFormatException e) {
			driver.switchTo().frame(indexNameOrId);
		}
	}
	
	/**
	 * closes all the child window assuming that the driver control is currently in the parent window
	 */
	public void closeAllChildWindows() {
		
		String parentWid=driver.getWindowHandle();
		
		Set<String> allWindowIds = driver.getWindowHandles();
		allWindowIds.remove(parentWid);
		
		for(String wid:allWindowIds) {
			driver.switchTo().window(wid);
			driver.close();
		}
		
		driver.switchTo().window(parentWid);
	}
	
	/**
	 * transfers the driver control to the parent window.
	 */
	public void swithToParentWindow() {
		driver.switchTo().window(new ArrayList<String>(driver.getWindowHandles()).get(0));
	}
	
	/**
	 * Transfers the driver control to the specific titled window.
	 * @param titleContains (It is case sensitive.)
	 */
	public void switchToWindow(String titleContains) {
		
		Set<String> allWindowIds = driver.getWindowHandles();
				
		for(String wid:allWindowIds) {
			driver.switchTo().window(wid);
			if(driver.getTitle().contains(titleContains)) {
				break;
			}
		}
	}
	
	
	/**
	 * used to click on ok or cancel button on any Javascript POpUp
	 * @param accept clicks on Ok if true otherwise it clicks on Cancel Button
	 */
	public void alertAccept(boolean accept) {
		
		wait.until(ExpectedConditions.alertIsPresent());
		
		if(accept) {
			driver.switchTo().alert().accept();
		} else {
			driver.switchTo().alert().dismiss();
		}
		
	}
	
	/**
	 * verifys the Javascript alert text is correct or not. 
	 * Returns true if the alert text contains the given alert text
	 * @param alertTextContains (Provide the partial text/full text. It is Case Sensitive)
	 * @return
	 */
	public boolean verifyAlertText(String alertTextContains) {
		wait.until(ExpectedConditions.alertIsPresent());
		return driver.switchTo().alert().getText().contains(alertTextContains);
	}

	public void waitForNotExist(WebElement targetElement) {
		wait.until(ExpectedConditions.invisibilityOf(targetElement));		
	}
	
}










