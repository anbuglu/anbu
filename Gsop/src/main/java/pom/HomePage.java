package pom;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import genericlibs.WebActionUtil;

public class HomePage extends BasePage {
	
	@FindBy(xpath="//div[@id='block_top_menu']/ul/li/a")
	private List<WebElement> menuLinksList;
	
	@FindBy(linkText="Sign out")
	private WebElement signOutLInk;
	
	public WebElement getSignOutLInk() {
		return signOutLInk;
	}

	public List<WebElement> getMenuLinksList() {
		return menuLinksList;
	}

	public HomePage(WebDriver driver, WebActionUtil webActionUtil) {
		super(driver, webActionUtil);
	}
	
	public void clickOnMenu(String menuName) {
		for(WebElement ele:menuLinksList) {
			if(ele.getText().equalsIgnoreCase(menuName)) {
				webActionUtil.click(ele);
				break;
			}
		}
	}
	
	public void signOut() {
		webActionUtil.click(signOutLInk);
	}
}