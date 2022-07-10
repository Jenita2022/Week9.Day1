package com.testleaf.pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentTest;
import com.testleaf.base.ProjectSpecificMethods;

public class LoginPage extends ProjectSpecificMethods{


	public LoginPage(ChromeDriver driver, ExtentTest node) {
		this.driver = driver;
		this.node = node;
	}

	public LoginPage enterUserName() throws IOException {
		try {
			driver.findElement(By.id("username")).sendKeys("DemoSalesManager");
			reportsStep("UserName is entered correctly","Pass");
		} catch (Exception e) {
			reportsStep("UserName is not entered correctly"+e,"Fail");
		}
		return this;
	}

	public LoginPage enterPassword() throws IOException {
		try {
			driver.findElement(By.id("password")).sendKeys("crmsfa");
			reportsStep("Password is entered correctly","Pass");
		} catch (Exception e) {
			reportsStep("Password is not entered correctly"+e,"Pass"); // concatinate e to see the log for failure
			e.printStackTrace();
		}
		return this;
	}

	public HomePage clickLogin() throws IOException {
		try {
			driver.findElement(By.className("decorativeSubmit")).click();
			reportsStep("Login button is clicked","Pass");
		} catch (Exception e) {
			reportsStep("Login button is not clicked" +e,"Fail");
		}
		return new HomePage(driver, node);
	}

}
