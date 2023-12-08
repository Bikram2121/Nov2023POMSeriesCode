package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By logo = By
			.xpath("//img[@src = 'https://naveenautomationlabs.com/opencart/image/catalog/opencart-logo.png']");
	private By searchField = By.xpath("//input[@placeholder='Search']");
	private By footer = By.xpath("//div[@class='col-sm-3']//li/a");

	private By registerLink = By.linkText("Register");

	private By loginErrMsg = By.cssSelector("div.alert.alert-danger.alert-dismissible");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	@Step("..getting the login page title...")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleIsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT,
				AppConstants.LOGIN_PAGE_TITLE_VALUE);
		System.out.println("Login page title is : " + title);
		return title;
	}

	@Step("...getting the logi page URL...")
	public String getLoginPageURL() {
		String url = eleUtil.waitForUrlContainsAndFetch(AppConstants.DEFAULT_SHORT_TIME_OUT,
				AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE);
		System.out.println("Login page url is : " + url);
		return url;
	}

	@Step("...getting the forget password link...")
	public Boolean isForgotPwdLinkExist() {
		return eleUtil.waitForElementVisible(forgotPwdLink, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}

	@Step("login with username:{0} and password:{1}")
	public AccountsPage doLogin(String un, String pwd) {
		System.out.println("app credentials are : " + un + ":" + pwd);
		eleUtil.waitForElementVisible(emailId, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(un);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);
	}

	@Step("login with wrong username:{0} and password:{1}")
	public boolean doLoginWithWrongData(String un, String pwd) {
		System.out.println("app credentials are : " + un + ":" + pwd);
		eleUtil.waitForElementVisible(emailId, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(un);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return eleUtil.waitForElementVisible(loginErrMsg, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}

	@Step("...getting the page logo...")
	public Boolean isLogoExist() {
		return eleUtil.waitForElementVisible(logo, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}

	@Step("...getting the search field...")
	public Boolean isSearchFieldExist() {
		return eleUtil.waitForElementVisible(searchField, AppConstants.DEFAULT_MEDIUM_TIME_OUT).isDisplayed();
	}

	@Step("...getting the footer links...")
	public List<String> getLoginFooterLinks() {
		List<WebElement> loginFooterLinks = eleUtil.waitForElementsVisible(footer, AppConstants.DEFAULT_LONG_TIME_OUT);
		List<String> actLoginFooterLinks = new ArrayList<String>();
		for (WebElement e : loginFooterLinks) {
			String text = e.getText();
			actLoginFooterLinks.add(text);
		}
		return actLoginFooterLinks;

	}

	@Step("...navigating to registration page...")
	public RegistrationPage navigateToRegisterPage() {
		eleUtil.doClick(registerLink);
		return new RegistrationPage(driver);
	}

}
