package listener;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class MyTestNGListener implements ITestListener{
	
	public void onTestFailure(ITestResult res) {
		System.out.println("Test Failed!!");
		System.out.println("Method Name: "+res.getName());
		//System.out.println(res.getTestContext().getAttribute(name));
		System.out.println(res.getStatus()); //2 represents failure status
		System.out.println(res.getThrowable().getMessage());
		
		ExtentTest test = (ExtentTest) res.getTestContext().getAttribute("test");
		test.log(Status.FAIL, res.getThrowable().getMessage());
		//Reporter.getCurrentTestResult().getTestContext().setAttribute("criticalFailure", "yes");

	}
	
	public void onTestSuccess(ITestResult res) {
		System.out.println("Successful test: "+res.getName());
		
		ExtentTest test = (ExtentTest) res.getTestContext().getAttribute("test");
		test.log(Status.PASS, "Successful test: "+res.getName());
	}
	
	public void onTestSkipped(ITestResult res) {
		System.out.println("Test skipped: "+res.getName());
		
		ExtentTest test = (ExtentTest) res.getTestContext().getAttribute("test");
		test.log(Status.SKIP, "Skipped test: "+res.getName());
	}
}
