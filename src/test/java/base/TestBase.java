package base;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import keywords.ApplicationKeywords;
import reports.ExtentManager;

public class TestBase {
    public ExtentReports rep;
    public ExtentTest test;
    public ApplicationKeywords app;
    
    @BeforeTest
    public void beforeTest(ITestContext context) throws FileNotFoundException, IOException {
        System.out.println("--Before Test--");
        String browser_name = context.getCurrentXmlTest().getParameter("browser");
        
        app = new ApplicationKeywords();
        rep = ExtentManager.getReports();
        test = rep.createTest(context.getCurrentXmlTest().getName());

        context.setAttribute("app", app);
        context.setAttribute("test", test);
        context.setAttribute("report", rep);
        context.setAttribute("browser", browser_name);
        app.setReport(test);
    }

    @BeforeMethod
    public void beforeMethod(ITestContext context) {
        System.out.println("--Before Method--");
        test = (ExtentTest) context.getAttribute("test");
        app = (ApplicationKeywords) context.getAttribute("app");
        rep = (ExtentReports) context.getAttribute("report");
    }

    @AfterTest
    public void afterTest(ITestContext context) {
        app = (ApplicationKeywords) context.getAttribute("app");
        rep = (ExtentReports) context.getAttribute("report");

        //if(app!=null) {
          //  app.quit();
        //}

        if(rep!=null) {
            rep.flush();
        }
    }
}
