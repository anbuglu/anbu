package pom;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import genericlibs.WebActionUtil;

public class ProductsListPage extends BasePage {
	
	@FindBy(xpath="//a[@class='product_img_link']")
	private List<WebElement> productsList;
	
	public List<WebElement> getProductsList() {
		return productsList;
	}

	public ProductsListPage(WebDriver driver, WebActionUtil webActionUtil) {
		super(driver, webActionUtil);
	}
	
	public void clickOnProduct(int productId) {
		String product = "id_product="+productId;
		for(WebElement ele:productsList) {
			if(ele.getAttribute("href").contains(product)) {
				webActionUtil.jsClick(ele);
				break;
			}
		}
	}
}
