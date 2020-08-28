package com.qa.Thinkbridgee;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Flightsearch {
	public static WebDriver driver;
	static Alert alert;
	WebDriverWait wait;

	@BeforeMethod
	public void setUp() throws Throwable {

		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http:jt-dev.azurewebsites.net/#/SignUp");
		// Thread.sleep(5000);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	@Test
	public void validatelanguage() {
		String[] exp = { "English", "Dutch" };
		WebElement dropdown = driver.findElement(By.xpath("//div[@class='ui-select-choices-row ng-scope active']"));
		//*[@id="language"]
		Select select = new Select(dropdown);

		List<WebElement> options =  select.getOptions();
		System.out.println(" size of dropdown"+ select.getOptions().size());
		for (WebElement we : options) {
			System.out.println("lang" + we.getText());
			boolean match = false;
			for (int i = 0; i < exp.length; i++) {
				if (we.getText().equals(exp[i])) {
					match = true;
				}
			}
			Assert.assertTrue(match);
		}

		
	}

	

	@Test
	public void validateSignup() {

		driver.findElement(By.xpath("//input[@id='name']")).sendKeys("poonam");
		driver.findElement(By.xpath("//input[@id='orgName']")).sendKeys("poonam");
		driver.findElement(By.xpath("//input[@id='singUpEmail']")).sendKeys("poonamkakad22@gmail.com");

		driver.findElement(By.xpath("//a[text()='Terms and Conditions' ]//preceding-sibling::span")).click();

		System.out.println("checkbox click");

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@class='form-group custom-form-group']")));

		driver.findElement(By.xpath("//div[@class='form-group custom-form-group']")).click();
		System.out.println("sinup.....click");
		if (isAlertPresent() == true) {
			driver.switchTo().alert().getText();
			System.out.println("alert msg " + driver.switchTo().alert().getText());

		} else {
			System.out.println("alert is not present");
		}
		String welcomemsg = driver.findElement(By.xpath("//div[@class='alert alert-danger alert-custom']")).getText();

		System.out.println("welcome msg is  >> " + welcomemsg);
		Assert.assertEquals(welcomemsg, "A welcome email has been sent. Please check your email.");
		

	}

	public boolean isAlertPresent() {
		try {

			driver.switchTo().alert();

			return true;
		} catch (NoAlertPresentException Ex) {

			return false;
		}
	}

}
