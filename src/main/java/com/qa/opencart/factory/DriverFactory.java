package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.qa.opencart.exception.FrameworkException;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;

	public static String highlight;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * this method is initializing the driver on the basis of given browser name
	 * 
	 * @param browserName
	 * @return this returns the driver
	 */

	public WebDriver initDriver(Properties prop) {
		optionsManager = new OptionsManager(prop);
		highlight = prop.getProperty("highlight").trim();

//		String browserName = System.getProperty("browser");
//		System.out.println("Browser name is : " + browserName);
//		
//		if (browserName==null) {
//			System.out.println("No valid browser is passed...");
//		}
//		else {
//			switch (browserName) {
//			case "edge":
//				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
//				break;
//				
//			case "firefox":
//				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
//				break;
//				
//			default:
//				System.out.println("please pass the correct browser...." + browserName);
//				break;
//			}
//		}

		String browserName = prop.getProperty("browser").toLowerCase().trim();
		System.out.println("Browser name is : " + browserName);

		if (browserName.equalsIgnoreCase("edge")) {

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// run at the remote/grid :

				init_remoteDriver("edge");

			} else {
				// local execution
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			}

		} else if (browserName.equalsIgnoreCase("firefox")) {

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// run at the remote/grid:

				init_remoteDriver("firefox");
			} else {
				// local execution
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}

		} else if (browserName.equalsIgnoreCase("chrome")) {

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// run at the remote/grid:

				init_remoteDriver("chrome");
			} else {
				// local execution
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}

		} else {
			System.out.println("please pass the correct browser name...." + browserName);
			throw new FrameworkException("WRONG BROWSER IS PASSED...");
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url").trim());
		return getDriver();

	}

	/**
	 * this method is called internally to initialize the driver with
	 * RemoteWebDriver
	 * 
	 * @param browser
	 */

	private void init_remoteDriver(String browser) {

		System.out.println("Running test on grid server : " + browser);

		try {
			switch (browser.toLowerCase()) {
			case "chrome":
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
				break;
			case "firefox":
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
				break;
			default:
				System.out.println("please pass the right browser for remote execution...");
				throw new FrameworkException("NO REMOTEBROWSER EXCEPTION");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get the local thread copy of the driver
	 * 
	 * @return
	 */
	public synchronized static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * this method is reading the properties from the .properties file
	 * 
	 * @return
	 */

	public Properties initProp() {

		// mvn clean install -Denv="qa"
		// mvn clean install
		prop = new Properties();
		FileInputStream ip = null;

		String envName = System.getProperty("env");
		System.out.println("Running test cases on Env: " + envName);

		try {
			if (envName == null) {
				System.out.println("no env is passed...Running tests on QA env...");
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} else {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;

				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;

				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;

				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;

				default:
					System.out.println("Wrong env is passed...no need to run the test cases");
					throw new FrameworkException("WRONG ENV IS PASSED...");
				// break;
				}
			}
		} catch (FileNotFoundException e) {
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	/**
	 * take the screenshot
	 * 
	 * @return
	 * 
	 */
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtil.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
