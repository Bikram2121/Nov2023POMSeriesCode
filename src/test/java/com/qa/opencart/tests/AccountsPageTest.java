package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.pages.SearchPage;

public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void accountsPageSetup() {
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test(priority = 1)
	public void accPageTitleTest() {
		String actTitle = accountsPage.getAccPageTitle();
		Assert.assertEquals(actTitle, AppConstants.ACCOUNT_PAGE_TITLE_VALUE);
	}

	@Test(priority = 2)
	public void accPageURLTest() {
		String actURL = accountsPage.getAccPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.ACCOUNT_PAGE_URL_FRACTION_VALUE));
	}

	@Test(priority = 3)
	public void isLogoutLinkExistTest() {
		boolean logoutLink = accountsPage.isLogoutLinkExist();
		Assert.assertTrue(logoutLink);
	}

	@Test(priority = 4)
	public void accPageHeadersCountTest() {
		List<String> actualAccPageHeadersList = accountsPage.getAccountsPageHeadersList();
		System.out.println("Accounts page Headers List : " + actualAccPageHeadersList);
		Assert.assertEquals(actualAccPageHeadersList.size(), AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
	}

	@Test(priority = 5)
	public void accPageHeadersValueTest() {
		List<String> actualAccPageHeadersList = accountsPage.getAccountsPageHeadersList();
		System.out.println("Actual Accounts page Headers List : " + actualAccPageHeadersList);
		System.out.println("Expected Accounts page Headers List : " + AppConstants.EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST);
		Assert.assertEquals(actualAccPageHeadersList, AppConstants.EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST);
	}

	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] { { "Apple" }, { "iMac" }, { "Macbook" }, { "Samsung" }, };
	}

	@Test(priority = 6, dataProvider = "getProductData")
	public void searchProductCountTest(String searchKey) {
		searchPage = accountsPage.performSearch(searchKey);
		Assert.assertTrue(searchPage.getSearchProductsCount() > 0);
	}

	@DataProvider
	public Object[][] getProductTestData() {
		return new Object[][] {

				{ "Macbook", "MacBook Pro" }, { "Macbook", "MacBook Air" }, { "apple", "Apple Cinema 30\"" },
				{ "imac", "iMac" }, { "Samsung", "Samsung SyncMaster 941BW" }, { "samsung", "Samsung Galaxy Tab 10.1" },

		};

	}

	@Test(priority = 7, dataProvider = "getProductTestData")
	public void searchProductTest(String searchKey, String productName) {
		searchPage = accountsPage.performSearch(searchKey);
		if (searchPage.getSearchProductsCount() > 0) {
			productInfoPage = searchPage.selectProduct(productName);
			String actProductHeader = productInfoPage.getProductHeaderValue();
			Assert.assertEquals(actProductHeader, productName);
		}
	}

}
