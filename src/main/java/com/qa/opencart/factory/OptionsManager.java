package com.qa.opencart.factory;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class OptionsManager {

	private Properties prop;
	private EdgeOptions eo;
	private FirefoxOptions fo;
	private ChromeOptions co;

	public OptionsManager(Properties prop) {
		this.prop = prop;
	}

	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();
		co.addArguments("--remote-allow-origins=*");
		Map<String, Object> selenoidOptions = new HashMap<>();

		if (Boolean.parseBoolean(prop.getProperty("remote"))) {
			co.setBrowserVersion(prop.getProperty("browserversion"));
			selenoidOptions.put("browsername", "chrome");
			selenoidOptions.put("enableVNC", true);
			selenoidOptions.put("name", prop.getProperty("testcasename"));
			co.setCapability("selenoid:options", selenoidOptions);
		}

		if (Boolean.parseBoolean(prop.getProperty("headless").trim())) {
			System.out.println("============Running chrome in headless===========");
			co.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito").trim())) {
			System.out.println("============Running chome in incognito===========");
			co.addArguments("--incognito");
		}
		return co;
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
