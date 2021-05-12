package testscripts;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import genericlibs.BaseTest;
import genericlibs.ExcelLibrary;
import pom.OrderDetailsPage;
import pom.ProductDetailsPage;
import pom.ProductsListPage;

@Listeners(genericlibs.Listeners.class)
public class TC001 extends BaseTest {
	
	@Test(description="To verify whether added product is displayed in the ODP or not")
	public void testAddToKart() {
				
		//Read the test data from the excel sheet
		String sheetName="TC001";
		String menuName=ExcelLibrary.getStringData(sheetName, 1, 0);
		int productId=(int)ExcelLibrary.getNumericData(sheetName, 1, 1);
		int quantity=(int)ExcelLibrary.getNumericData(sheetName, 1, 2);
		boolean increase=ExcelLibrary.getBooleanData(sheetName, 1, 3);
		String size=ExcelLibrary.getStringData(sheetName, 1, 4);
		String colorName=ExcelLibrary.getStringData(sheetName, 1, 5);
		
		//Write the test steps
		//1. click on Dresses Menu
		hp.clickOnMenu(menuName);
		
		//2. click on Product based on product ID
		ProductsListPage plp = new ProductsListPage(driver, webActionUtil);
		plp.clickOnProduct(productId);
		
		//3. Add the product to the Kart using quantity, size and colorName
		ProductDetailsPage pdp = new ProductDetailsPage(driver, webActionUtil);
		pdp.addItemToKart(quantity, increase, size, colorName);
		
		//4. Verify if the Added product is displayed in ODP
		OrderDetailsPage odp = new OrderDetailsPage(driver, webActionUtil);
		Assert.assertTrue(odp.verifyProductIsDisplayed(productId));
		
	}
}
