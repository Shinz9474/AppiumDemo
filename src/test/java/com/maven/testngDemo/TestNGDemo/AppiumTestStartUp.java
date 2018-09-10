package com.maven.testngDemo.TestNGDemo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import net.bytebuddy.utility.privilege.GetSystemPropertyAction;

public class AppiumTestStartUp {
	private static String Appium_Node_Path = "C:\\Program Files\\nodejs\\node.exe";
	private static String Appium_JS_Path = "C:\\Users\\#$h!nz#\\AppData\\Local\\Programs\\Appium\\resources\\app\\node_modules\\appium\\build\\lib\\main.js";
	private static AppiumDriverLocalService appiumService;

	public static SimpleDateFormat df = new SimpleDateFormat("MM-dd-yy HH:mm:ss.SSS");

	@BeforeTest
	public static void testSetUp() {
		System.out.println("Starting Appium server" + df.format(new Date()));
		startAppiumServer();
	}

	@AfterTest
	public static void testCleanUp() {
		System.out.println("Stopping Appium server " + df.format(new Date()));
		stopAppiumServer();
	}

	//@Test()
	@Test(priority = 1)
	public static void testCase() {
		AppiumDriver<MobileElement> driver = null;
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("deviceName", "OnePlus 3T");
		caps.setCapability("udid", "de7af825"); // Give Device ID of your mobile phone
		caps.setCapability("platformName", "Android");
		caps.setCapability("platformVersion", "8.0");
		/*
		 * caps.setCapability("appPackage", "com.experitest.ExperiBank");
		 * caps.setCapability("appActivity", ".LoginActivity");
		 */
		caps.setCapability("browserName", "Chrome");
		caps.setCapability("noReset", "true");

		//while (appiumService.isRunning()) {
			try {
				driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
				driver.get("https://www.google.com");
				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				driver.findElementByName("q").sendKeys("Sanju moview review");
				//driver.findElement(By.xpath("//input[@id='lst-ib']")).sendKeys("Sanju movie review");
				((AndroidDriver)driver).pressKeyCode(AndroidKeyCode.ENTER);
				Thread.sleep(9000);
				driver.close();
				//break;
			}

			catch (Exception ex) {
				System.out.println("Unable to launch app.\nFollowing exception occured while launching the app: "
						+ ex.getMessage());

			}
		}

	//}

	public static void startAppiumServer() {
		try {
			appiumService = AppiumDriverLocalService
					.buildService(new AppiumServiceBuilder().usingDriverExecutable(new File(Appium_Node_Path))
							.withAppiumJS(new File(Appium_JS_Path)).withIPAddress("127.0.0.1").usingPort(4723));
			appiumService.start();
		} catch (Exception e) {
			System.out.println("Unable to start appium server.\nFollowing exception occured while starting the server: "
					+ e.getMessage());
		}
	}

	public static void stopAppiumServer() {
		appiumService.stop();
	}

}
