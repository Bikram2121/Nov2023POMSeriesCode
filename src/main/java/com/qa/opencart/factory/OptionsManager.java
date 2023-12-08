package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

	private Properties prop;
	private EdgeOptions eo;
	private FirefoxOptions fo;

	public OptionsManager(Properties prop) {
		this.prop = prop;
	}

	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless").trim())) {
			System.out.println("============Running edge in headless===========");
			eo.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("InPrivate").trim())) {
			System.out.println("==============Running edge in InPrivate===============");
			eo.addArguments("--InPrivate");
		}
		return eo;
	}

	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless").trim())) {
			System.out.println("============Running firefox in headless===========");
			fo.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("private").trim())) {
			System.out.println("============Running firefox in private===========");
			fo.addArguments("--private");
		}
		return fo;
	}

}
