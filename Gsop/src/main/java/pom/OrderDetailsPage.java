package pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import genericlibs.WebActionUtil;

public class OrderDetailsPage extends BasePage {
	
	String deleteIconXp="//td[@class='cart_product']/a[contains(@href,'productId')]/../following-sibling::td[@data-title='Delete']//i";
	
	@FindBy(xpath="//td[@class='cart_product']/a")
	private List<WebElement> productsList;
	
	public List<WebElement> getProductsList() {
		return productsList;
	}

	public OrderDetailsPage(WebDriver driver, WebActionUtil webActionUtil) {
		super(driver,webActionUtil);
	}
	
	public boolean verifyProductIsDisplayed(int productId) {
		
		String product = "id_product="+productId;
		for(WebElement ele:productsList) {
			if(ele.getAttribute("href").contains(product)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void deleteProduct(int productId) {
		String product = "id_product="+productId;
		deleteIconXp=deleteIconXp.replace("productId", product);
		
		WebElement deleteIcon=driver.findElement(By.xpath(deleteIconXp));
		webActionUtil.click(deleteIcon);
		webActionUtil.waitForNotExist(deleteIcon);
	}
}
