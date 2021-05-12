package pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import genericlibs.WebActionUtil;

public class LoginPage extends BasePage {
	
	@FindBy(linkText="Sign in")
	private WebElement siginLink;
	
	@FindBy(id="email")
	private WebElement emailTextField;
	
	@FindBy(id="passwd")
	private WebElement passwordTextField;
	
	@FindBy(id="SubmitLogin")
	private WebElement signInButton;
		
	public WebElement getSiginLink() {
		return siginLink;
	}

	public WebElement getEmailTextField() {
		return emailTextField;
	}

	public WebElement getPasswordTextField() {
		return passwordTextField;
	}

	public WebElement getSignInButton() {
		return signInButton;
	}

	public LoginPage(WebDriver driver, WebActionUtil webActionUtil) {
		super(driver, webActionUtil);
	}
	
	/**
	 * An Action method to login to the Appliction with the given credentails
	 * @param emailId
	 * @param emailPassword
	 */
	public void signIn(String emailId, String emailPassword) {
		webActionUtil.click(siginLink);
		webActionUtil.enterData(emailTextField, emailId);
		webActionUtil.enterData(passwordTextField, emailPassword);
		webActionUtil.click(signInButton);
	}
}