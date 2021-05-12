package testscripts;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import genericlibs.BaseTest;
import genericlibs.ExcelLibrary;
import pom.OrderDetailsPage;
import pom.ProductDetailsPage;
import pom.ProductsListPage;

public class TC002 extends BaseTest {
	
	@DataProvider
	public String[][] getData(){
		return ExcelLibrary.getMultipleData("TC002");
	}
	
	
	@Test(dataProvider="getData")
	public void testAddToKart(String menuName,
							  String productIdString,
							  String quantityString,
							  String increase,
							  String size,
							  String colorName) {
			
		int productId=(int)Double.parseDouble(productIdString);
		int quantity=(int)Double.parseDouble(quantityString);
		
		//Write the test steps
		//1. click on Dresses Menu
		hp.clickOnMenu(menuName);
		
		//2. click on Product based on product ID
		ProductsListPage plp = new ProductsListPage(driver, webActionUtil);
		plp.clickOnProduct(productId);
		
		//3. Add the product to the Kart using quantity, size and colorName
		ProductDetailsPage pdp = new ProductDetailsPage(driver, webActionUtil);
		pdp.addItemToKart(quantity, Boolean.parseBoolean(increase), size, colorName);
		
		//4. Verify if the Added product is displayed in ODP
		OrderDetailsPage odp = new OrderDetailsPage(driver, webActionUtil);
		Assert.assertTrue(odp.verifyProductIsDisplayed(productId));
		
		
	}
}