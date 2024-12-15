package reports;

import java.io.File;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	static 	ExtentReports reports;
	public static String screenshotFolderPath;
	
	public static ExtentReports getReports() {
		if(reports == null) {
			reports = new ExtentReports();
			
			Date d = new Date();
			System.out.println(d.toString().replaceAll(":", "-"));
			String date_time = d.toString().replaceAll(":", "-");
			String folderName = date_time+"\\Screenshots\\";
			String path = System.getProperty("user.dir")+"\\reports\\"+folderName;
			File f = new File(path);
			f.mkdirs();
			System.out.println(path);
			String report_path = System.getProperty("user.dir")+"\\reports\\"+date_time;
			screenshotFolderPath = System.getProperty("user.dir")+"\\reports\\"+date_time+"\\Screenshots\\";
			//System.out.println(report_path);
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(report_path);
			
			sparkReporter.config().setReportName("Production Testing");
			sparkReporter.config().setDocumentTitle("Automation testing");
			sparkReporter.config().setTheme(Theme.STANDARD);
			sparkReporter.config().setEncoding("utf-8");
			
			reports.attachReporter(sparkReporter);
		}
		
		return reports;
	}
}
