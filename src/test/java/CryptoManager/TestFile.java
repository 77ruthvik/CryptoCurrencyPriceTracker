package CryptoManager;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import base.TestBase;

public class TestFile extends TestBase{

    @Test(priority = 1)
    public void searchCrypto(ITestContext context) {
        String browser = (String) context.getAttribute("browser");
        String crypto_name = (String) context.getCurrentXmlTest().getParameter("name");

        app.openBrowser(browser);
        app.navigate("url");
        app.waitForPageToLoad();
        app.click("search_button_class");
        app.wait(1);
        app.type("search_bar_class", crypto_name);
        app.wait(1);
        app.enter("search_bar_class");

        app.assertAll();
    }

    @Test(priority = 2)
    public void getData(ITestContext context) {
        String crypto_name = (String) context.getCurrentXmlTest().getParameter("name");
        String threshold_val = (String) context.getCurrentXmlTest().getParameter("threshold");

        app.wait(1);
        String cur_value = app.getValue("value_xpath");

        double value = app.replaceSymbols(cur_value);
        double threshold = app.replaceSymbols(threshold_val);

        app.log("Current value of "+crypto_name+" is: "+value);
        app.log("Threshold defined is: "+threshold);
        
        if(value<threshold) {
            app.reportFailure("Current value of "+crypto_name+" is lower than threshold!!", true);
        }

        app.assertAll();
    }
}
