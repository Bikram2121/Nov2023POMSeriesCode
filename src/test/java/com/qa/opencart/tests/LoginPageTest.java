package com.qa.opencart.tests;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EPIC - 100: design login for open cart app")
@Story("US -Login: 101: design login features for open cart")
public class LoginPageTest extends BaseTest {

	private final Logger logger = Logger.getLogger(LoginPageTest.class);

	@Severity(SeverityLevel.TRIVIAL)
	@Description("checking the title of the page..tester : Vikram")
	@Test(priority = 1)
	public void loginPageTitleTest() {
		MDC.put("testClassName", this.getClass().getSimpleName());
		logger.info("This is a log message from loginPageTitleTest");
		String actulaTitle = loginPage.getLoginPageTitle();
		logger.info("actual login page title is : " + actulaTitle);
		Assert.assertEquals(actulaTitle, AppConstants.LOGIN_PAGE_TITLE_VALUE);
	}

	@Severity(SeverityLevel.NORMAL)
	@Description("getting the URL of the page")
	@Test(priority = 2)
	public void loginPageURLTest() {
		MDC.put("testClassName", this.getClass().getSimpleName());
		logger.info("This is a log message from loginPageURLTest");
		String actualURL = loginPage.getLoginPageURL();
		logger.info("actual login page url is : " + actualURL);
		Assert.assertTrue(actualURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE));
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("...checking forgot password link exist...tester : Vikram")
	@Test(priority = 3)
	public void forgotPwdLinkExistTest() {
		MDC.put("testClassName", this.getClass().getSimpleName());
		logger.info("This is a log message from forgotPwdLinkExistTest");
		Boolean flag = loginPage.isForgotPwdLinkExist();
		Assert.assertTrue(flag);
	}

	@Severity(SeverityLevel.BLOCKER)
	@Description("...checking user is able to login to app with correct username and password...")
	@Test(priority = 7)
	public void loginTest() {
		MDC.put("testClassName", this.getClass().getSimpleName());
		logger.info("This is a log message from loginTest");
		accountsPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
		Assert.assertTrue(accountsPage.isLogoutLinkExist());
	}

	// @DataProvider
	// public Object[][] getWrongLoginData() {
	// Object loginData[][] = ExcelUtil.getTestData("login");
	// return loginData;
	// }
	//
	// @Test(priority = 1 , dataProvider = "getWrongLoginData")
	// public void loginWithWrongDataTest(String username, String password) {
	// boolean flag = loginPage.doLoginWithWrongData(username, password);
	// Assert.assertTrue(flag);
	// }

	@Severity(SeverityLevel.CRITICAL)
	@Description("...checking the logo of the page exists...")
	@Test(priority = 4)
	public void logoExistTest() {
		MDC.put("testClassName", this.getClass().getSimpleName());
		logger.info("This is a log message from logoExistTest");
		Boolean actuallogo = loginPage.isLogoExist();
		Assert.assertTrue(actuallogo);
	}

	@Severity(SeverityLevel.BLOCKER)
	@Description("...checking the search bar of the page exists...")
	@Test(priority = 5)
	public void searchFieldExistTest() {
		MDC.put("testClassName", this.getClass().getSimpleName());
		logger.info("This is a log message from searchFieldExistTest");
		boolean search = loginPage.isSearchFieldExist();
		Assert.assertTrue(search);
	}

	@Severity(SeverityLevel.NORMAL)
	@Description("..checking the footer links exists...")
	@Test(priority = 6)
	public void actFooterLinksTest() {
		MDC.put("testClassName", this.getClass().getSimpleName());
		logger.info("This is a log message from actFooterLinksTest");
		List<String> actFooterLinks = loginPage.getLoginFooterLinks();
		logger.info("Login page footer links : " + actFooterLinks);
		Assert.assertEquals(actFooterLinks.size(), AppConstants.LOGIN_PAGE_HEADERS_COUNT);
	}

}
