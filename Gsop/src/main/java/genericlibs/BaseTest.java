package genericlibs;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;
import pom.HomePage;
import pom.LoginPage;

//The class which contains common preconditions and postconditions for all the test cases
public class BaseTest implements IAutoConstants {
	
	public WebDriver driver;
	public WebActionUtil webActionUtil;
	public HomePage hp;
	
	@Parameters({"browserName", "appUrl", "implicit", "explicit"})
	@BeforeClass(alwaysRun=true)
	public void openApp(@Optional(DEFAULT_BROWSER)String browserName,
						@Optional(APP_URL)String appUrl,
						@Optional(ITO)String implicit,
						@Optional(ETO)String explicit) {
		
		if(browserName.equalsIgnoreCase("chrome")) {
			//System.setProperty(CHROME_KEY, CHROME_PATH);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);
		} else if(browserName.equalsIgnoreCase("firefox")) {
			//System.setProperty(GECKO_KEY, GECKO_PATH);
			FirefoxOptions options = new FirefoxOptions();
			options.addPreference("dom.webnotifications.enabled", false);
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(options);
		} else {
			Assert.fail("Given browser "+browserName+" is not supported");
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Long.parseLong(implicit), TimeUnit.SECONDS);
		driver.get(appUrl);
		webActionUtil = new WebActionUtil(driver, Long.parseLong(explicit));
	}
	
	@Parameters({"emailId", "password"})
	@BeforeMethod(alwaysRun=true)
	public void loginToApp(@Optional(DEFAULT_USER)String email,
						   @Optional(DEFAULT_PASSWORD)String passd) {
		LoginPage lp = new LoginPage(driver, webActionUtil);
		lp.signIn(email, passd);
		hp = new HomePage(driver, webActionUtil);
	}
	
	@AfterMethod(alwaysRun=true)
	public void logoutFromApp(ITestResult result) {
		
		if(result.getStatus()==ITestResult.FAILURE) {
			String path=FrameworkUtil.getSelfie(driver, result.getName());
			System.out.println(path);
		}
		
		hp.signOut();
	}
	
	
	@AfterClass(alwaysRun=true)
	public void closeApp() {
		driver.quit();
	}
}
