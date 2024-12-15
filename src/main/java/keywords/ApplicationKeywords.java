package keywords;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

public class ApplicationKeywords extends GenericKeywords {
	
	public ApplicationKeywords() throws FileNotFoundException, IOException {
		String path = System.getProperty("user.dir")+"//src//test//resources//env.properties";
		prop = new Properties();
		prop.load(new FileInputStream(path));

		softAssert = new SoftAssert();
	}

	public void setReport(ExtentTest test){
		this.test = test;
	}

	public double replaceSymbols(String val) {
		String cleaned_price = val.replaceAll("[$]", "");
        String remove_commas = cleaned_price.replaceAll(",", "");

		double value = Double.parseDouble(remove_commas);

		return value;
	}

	public void quit() {
		log("Closing Browser");
		driver.quit();
	}
}
