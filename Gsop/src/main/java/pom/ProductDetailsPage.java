package pom;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import genericlibs.WebActionUtil;

public class ProductDetailsPage extends BasePage {
	
	
	@FindBy(className="icon-plus")
	private WebElement plusIcon;
	
	@FindBy(className="icon-minus")
	private WebElement minusIcon;
	
	@FindBy(id="group_1")
	private WebElement sizeListBox;
	
	@FindBy(xpath="//ul[@id='color_to_pick_list']/li/a")
	private List<WebElement> colorPickerList;
	
	@FindBy(name="Submit")
	private WebElement addToKartButton;
	
	@FindBy(linkText="Proceed to checkout")
	private WebElement proceedToCheckout;
	
	@FindBy(xpath="//span[@title='Continue shopping']")
	private WebElement continueToShopping;
		
	public WebElement getPlusIcon() {
		return plusIcon;
	}

	public WebElement getMinusIcon() {
		return minusIcon;
	}

	public WebElement getSizeListBox() {
		return sizeListBox;
	}

	public List<WebElement> getColorPickerList() {
		return colorPickerList;
	}

	public WebElement getAddToKartButton() {
		return addToKartButton;
	}

	public WebElement getProceedToCheckout() {
		return proceedToCheckout;
	}

	public WebElement getContinueToShopping() {
		return continueToShopping;
	}

	public ProductDetailsPage(WebDriver driver, WebActionUtil webActionUtil) {
		super(driver, webActionUtil);
	}
	
	public void increaseOrDecrease(int quantity, boolean increase) {
		
		WebElement targetIcon;
		
		if(increase) {
			targetIcon = plusIcon;
		} else {
			targetIcon = minusIcon;
		}
		
		for(int i=1;i<=quantity;i++) {
			webActionUtil.click(targetIcon);
		}
	}
	
	public void selectColor(String colorName) {
		for(WebElement ele:colorPickerList) {
			if(ele.getAttribute("name").equalsIgnoreCase(colorName)) {
				webActionUtil.click(ele);
				break;
			}
		}
	}
	
	public void addItemToKart(int quantity, boolean increase,
							  String size, String colorName) {
		
		increaseOrDecrease(quantity,increase);
		webActionUtil.selectByVisibleText(sizeListBox, size);
		selectColor(colorName);
		webActionUtil.click(addToKartButton);
		webActionUtil.click(proceedToCheckout);
	}
}