package com.qa.opencart.tests;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountsPageTest extends BaseTest {

	private final Logger logger = Logger.getLogger(AccountsPageTest.class);

	@BeforeClass
	public void accountsPageSetup() {
		accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test(priority = 1)
	public void accPageTitleTest() {
		MDC.put("testClassName", this.getClass().getSimpleName());
		String actTitle = accountsPage.getAccPageTitle();
		logger.info("actual accounts page title is : " + actTitle);
		Assert.assertEquals(actTitle, AppConstants.ACCOUNT_PAGE_TITLE_VALUE);
	}

	@Test(priority = 2)
	public void accPageURLTest() {
		MDC.put("testClassName", this.getClass().getSimpleName());
		String actURL = accountsPage.getAccPageURL();
		logger.info("actual accounts page url is : " + actURL);
		Assert.assertTrue(actURL.contains(AppConstants.ACCOUNT_PAGE_URL_FRACTION_VALUE));
	}

	@Test(priority = 3)
	public void isLogoutLinkExistTest() {
		MDC.put("testClassName", this.getClass().getSimpleName());
		boolean logoutLink = accountsPage.isLogoutLinkExist();
		Assert.assertTrue(logoutLink);
	}

	@Test(priority = 4)
	public void accPageHeadersCountTest() {
		MDC.put("testClassName", this.getClass().getSimpleName());
		List<String> actualAccPageHeadersList = accountsPage.getAccountsPageHeadersList();
		System.out.println("Accounts page Headers List : " + actualAccPageHeadersList);
		Assert.assertEquals(actualAccPageHeadersList.size(), AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
	}

	@Test(priority = 5)
	public void accPageHeadersValueTest() {
		MDC.put("testClassName", this.getClass().getSimpleName());
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
		MDC.put("testClassName", this.getClass().getSimpleName());
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
		MDC.put("testClassName", this.getClass().getSimpleName());
		searchPage = accountsPage.performSearch(searchKey);
		if (searchPage.getSearchProductsCount() > 0) {
			productInfoPage = searchPage.selectProduct(productName);
			String actProductHeader = productInfoPage.getProductHeaderValue();
			Assert.assertEquals(actProductHeader, productName);
		}
	}

}
