package com.qa.opencart.pages;

import org.openqa.selenium.By;

public class OrderPage {

	By loc = By.id("oder");
	By price = By.id("price");

	public void getOrder() {
		System.out.println("get order");
	}

	public void getPricec() {
		System.out.println("get price");
	}

}
