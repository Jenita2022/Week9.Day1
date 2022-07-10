package com.testleaf.testcases;

import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.testleaf.base.ProjectSpecificMethods;
import com.testleaf.pages.LoginPage;

public class TC001Login extends ProjectSpecificMethods{

	@BeforeTest
	public void setData() {
		testName = "TC001Login";
		testDescription ="Login for leaftaps" ;
		testCategory = "Smoke";
		testAuthor = "Jenita";
	}
	
	@Test
	public void runLogin() throws IOException {
		new LoginPage(driver, node).enterUserName()
		.enterPassword()
		.clickLogin();
	}
}
