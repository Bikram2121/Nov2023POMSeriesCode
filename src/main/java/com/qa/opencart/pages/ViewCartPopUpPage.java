package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ViewCartPopUpPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By productNames = By.cssSelector(".table.table-striped td.text-left a");
	private By productValues = By.cssSelector(".table.table.table-bordered tbody");

	public ViewCartPopUpPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public ArrayList<String> getProductsValueListInCart() {
		List<WebElement> cartList = eleUtil.waitForElementsVisible(productNames, AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		ArrayList<String> cartProductList = new ArrayList<String>();
		for (WebElement e : cartList) {
			String text = e.getText();
			cartProductList.add(text);
		}
		return cartProductList;
	}

//	public ArrayList<String> getProductsValueListInCart() {
//		List<WebElement> cartValuesList = eleUtil.waitForElementsVisible(productValues,
//				AppConstants.DEFAULT_MEDIUM_TIME_OUT);
//		ArrayList<String> cartValuesTotalList = new ArrayList<String>();
//		for (WebElement e : cartValuesList) {
//			String text = e.getText();
//			cartValuesTotalList.add(text);
//		}
//		return cartValuesTotalList;
//	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
