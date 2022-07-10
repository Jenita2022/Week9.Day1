package com.testleaf.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.utils.FileUtil;
import com.testleaf.utils.ReadExcel_New;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ProjectSpecificMethods {
	public ChromeDriver driver;	
	public String fileName;

	public static Properties prop1;// to access outside the class
	public static Properties prop;

	public static ExtentReports extent;
	public ExtentTest test, node; //Parallel exec - remove static for parallel exec and pass node in all pages - own constructor
	public String testName, testDescription, testCategory, testAuthor;
	
	@BeforeSuite
	public void startReports() {
		//common for all TCs
		ExtentHtmlReporter reporter = new ExtentHtmlReporter("./reports/result.html"); 
		reporter.setAppendExisting(true);
		extent = new ExtentReports(); 
		extent.attachReporter(reporter);
	}

	@BeforeClass
	public void testCaseDetails() {
		//for each TC
		ExtentTest test = extent.createTest(testName, testDescription);
		test.assignCategory(testCategory);
		test.assignAuthor(testAuthor);
	}

	public int takeSnap() throws IOException {
		// to generate unique random img file names.. 
		int randomNum= (int) (Math.random()*9999+1000); 
		File source = driver.getScreenshotAs(OutputType.FILE);
		File destination = new File("./screenshots/img"+randomNum+".png");
		FileUtils.copyFile(source, destination);

		return randomNum;
	}

	public void reportsStep(String stepDesc, String status) throws IOException {
		if(status.equalsIgnoreCase("Pass")) {
			//test.pass(stepDesc,MediaEntityBuilder.createScreenCaptureFromPath(".././screenShots/img"+takeSnap()+".png").build());
			node.pass(stepDesc,MediaEntityBuilder.createScreenCaptureFromPath(".././screenShots/img"+takeSnap()+".png").build());
		}else if(status.equalsIgnoreCase("fail")) {
			//test.fail(stepDesc,MediaEntityBuilder.createScreenCaptureFromPath(".././screenShots/img"+takeSnap()+".png").build());
			node.fail(stepDesc,MediaEntityBuilder.createScreenCaptureFromPath(".././screenShots/img"+takeSnap()+".png").build());
			throw new RuntimeException("See the Extent for failure log"); //this is not under try catch so runtime excep is not handled. so can stop exec if the step failed.
		}
	}
	
	@AfterSuite
	public void stopReport() {
		extent.flush();
	}

	@BeforeMethod
	public void precondition() throws IOException {
		//		WebDriverManager.chromedriver().setup();
		//		driver = new ChromeDriver();
		//		driver.get("http://leaftaps.com/opentaps");
		//		driver.manage().window().maximize();
		//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		
		node = test.createNode(testName); //node is subset of TC - when DataProvider gives multiple set of data, this node will provide report separately for each set of data.
		
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		//copy same code from readproperties file
		prop = new Properties(); //global variable so removed Properties
		FileInputStream fis = new FileInputStream(new File("./src/main/resources/config.properties"));
		prop.load(fis);
		driver.get(prop.getProperty("url"));//driver.get("http://leaftaps.com/opentaps");

		String langFile = prop.getProperty("lang"); // To access the lang specific properties file by passing the variable in file path

		prop1 = new Properties(); 
		FileInputStream fis1 = new FileInputStream(new File("./src/main/resources/"+langFile+".properties"));//access lang specific properties file
		prop1.load(fis1);

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

	}

	@AfterMethod
	public void postcondition() {
		driver.close();
	}
	@DataProvider
	public String[][] fetchdata() throws IOException{
		String[][] readData = ReadExcel_New.readData(fileName);
		return readData;
	}

}
