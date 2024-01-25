package com.qa.opencart.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.pages.ViewCartPopUpPage;
import com.qa.opencart.utils.ExcelUtil;

public class ProductPageInfoTest extends BaseTest {

	ArrayList<String> expProdListInCart;

	private final Logger logger = Logger.getLogger(ProductPageInfoTest.class);

	@BeforeClass
	public void productPageInfoSetup() {
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

//	@DataProvider
//	public Object[][] getProductTitleTestData() {
//		return new Object[][] { { "macbook", "MacBook Pro" }, { "macbook", "MacBook" },
//				{ "samsung", "Samsung SyncMaster 941BW" }, { "imac", "iMac" },
//
//		};
//	}

	@DataProvider
	public Object[][] getProductTitleTestData() {
		Object prodData[][] = ExcelUtil.getTestData(AppConstants.PRODUCTINFO_SHEET_NAME);
		return prodData;
	}

	@Test(dataProvider = "getProductTitleTestData")
	public void productTitleTest(String searchKey, String productName) {
		searchPage = accountsPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(productName);
		String actTitle = productInfoPage.getProductPageTitle(productName);
		Assert.assertEquals(actTitle, productName);
	}

	@DataProvider
	public Object[][] getProductImagesTestData() {
		return new Object[][] { { "macbook", "MacBook Pro", 4 }, { "macbook", "MacBook", 5 },
				{ "samsung", "Samsung SyncMaster 941BW", 1 }, { "apple", "Apple Cinema 30\"", 6 },

		};
	}

	@Test(dataProvider = "getProductImagesTestData")
	public void productImagesCountTest(String searchKey, String ProductName, int imagesCount) {
		searchPage = accountsPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(ProductName);
		int actImagesCount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(actImagesCount, imagesCount);
	}

	@Test(dataProvider = "getProductTitleTestData")
	public void productDescExistTest(String searchKey, String ProductName) {
		searchPage = accountsPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(ProductName);
		int actProdDesc = productInfoPage.getProductDescription();
		Assert.assertTrue(actProdDesc > 0);
	}

	@DataProvider
	public Object[][] getProductInfoTestData() {
		return new Object[][] { { "macbook", "MacBook Pro", "Apple", "Product 18" },
				{ "macbook", "MacBook", "Apple", "Product 16" }, { "imac", "iMac", "Apple", "Product 14" },

		};
	}

	@Test(dataProvider = "getProductInfoTestData")
	public void productInfoTest(String searchKey, String productName, String brandName, String productCode) {
		searchPage = accountsPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(productName);
		Map<String, String> actProductInfoMap = productInfoPage.getProductInfo();
		softAssert.assertEquals(actProductInfoMap.get("Brand"), brandName);
		softAssert.assertEquals(actProductInfoMap.get("Product Code"), productCode);
		softAssert.assertEquals(actProductInfoMap.get("productname"), productName);
		softAssert.assertAll();
	}

	@DataProvider
	public Object[][] addToCartTestData() {
		return new Object[][] { { "macbook", "MacBook Pro", 2, "Success" }, { "macbook", "MacBook", 4, "Success" },
				{ "imac", "iMac", 6, "Success" },

		};
	}

	@Test(dataProvider = "addToCartTestData")
	public void addToCartTest(String searchKey, String productName, int qty, String cartSuccessMsg) {
		searchPage = accountsPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(productName);
		productInfoPage.enterQuantity(qty);
		String actCartMsg = productInfoPage.addProductToCart();
		// success : you have added MacBook pro to your shopping cart
		softAssert.assertTrue(actCartMsg.contains(cartSuccessMsg));
		softAssert.assertTrue(actCartMsg.contains(productName));
		softAssert.assertEquals(actCartMsg,
				"" + cartSuccessMsg + ": You have added " + productName + " to your shopping cart!");

		// Checking cart details as well:

		viewCartPopUpPage = productInfoPage.openCart();
		List<String> cartProdList = viewCartPopUpPage.getProductsValueListInCart();
		Object[][] data = addToCartTestData();
		expProdListInCart = new ArrayList<String>();
		for (int i = 0; i < data.length; i++) {
			expProdListInCart.add(data[i][1].toString());

		}
		logger.info("expected product list in cart is : " + expProdListInCart);
		softAssert.assertTrue(expProdListInCart.containsAll(cartProdList));
		softAssert.assertAll();
	}

}
