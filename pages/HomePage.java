package com.testleaf.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.testleaf.base.ProjectSpecificMethods;

public class HomePage extends ProjectSpecificMethods {
	public HomePage(ChromeDriver driver, ExtentTest node) {
		this.driver = driver;
		this.node = node;
	}


public MyHomePage clickCRMSFALink(){
	driver.findElement(By.linkText("CRM/SFA")).click();
	return new MyHomePage(driver);
}
}
