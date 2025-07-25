package crm.listenersUtility;

import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ISuite;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import BaseClass.BaseClass;

public class ListnImpClass extends BaseClass implements ITestListener {

	public ExtentReports report;
	public ExtentTest test;

	public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	public void onStart(ISuite suite) {
		System.out.println("report configuration");
		//in order to setup and configuration report  
		String time = new Date().toString().replace(" ", "_").replace(":", "_");
		ExtentSparkReporter spark = new ExtentSparkReporter("./AdvanceReport/report_"+time+".html");
		spark.config().setDocumentTitle("CRM Test Suite Results");
		spark.config().setReportName("CRM Report");
		spark.config().setTheme(Theme.DARK);
		
		//in order to add environment info and create test
		 report = new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("OS", "windows-11");
		report.setSystemInfo("BROWSER", "CHROME");
	}

	public void onFinish(ISuite suite) {
		System.out.println("report backup");
		report.flush();
	}

	public void onTestStart(ITestResult result) {
		System.out.println("==== ====>" + result.getMethod().getMethodName() + "<====START====");
		 test = report.createTest(result.getMethod().getMethodName());
		 extentTest.set(test);
		 extentTest.get().log(Status.INFO, result.getMethod().getMethodName()+"==>STARTED<==");
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("==== ====>" + result.getMethod().getMethodName() + "<====END====");
		 extentTest.get().log(Status.PASS, result.getMethod().getMethodName()+"==>completed<==");

	}

	public void onTestFailure(ITestResult result) {
		TakesScreenshot ts = (TakesScreenshot)edriver;
		String filePath = ts.getScreenshotAs(OutputType.BASE64);
		
		String testName = result.getMethod().getMethodName();
		String time = new Date().toString().replace(" ", "_").replace(":", "_");
		test.addScreenCaptureFromBase64String(filePath,testName+"_"+time);
		extentTest.get().log(Status.FAIL, result.getMethod().getMethodName()+"==>FAILED<==");
		extentTest.get().log(Status.FAIL, result.getThrowable());		
	}

	public void onTestSkipped(ITestResult result) {
		ITestListener.super.onTestSkipped(result);
	}
	
}
