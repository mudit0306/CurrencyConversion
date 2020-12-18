package DemoProject.Currency_Converter;

import static org.testng.Assert.assertFalse;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Util {

	public ExtentReports report;
	public ExtentTest logger;

	public void setupExtentReport(String browser) {
		LocalDate today = LocalDate.now();

		ExtentHtmlReporter extent = new ExtentHtmlReporter(
				new File("./Reports/Currency_ConversionReport "+browser+ today +".html"));
		report = new ExtentReports();
		report.attachReporter(extent);

		 logger=report.createTest("Currency Conversion Test");
	}

	public static void selectCountryFromDropdown(List<WebElement> countryList, String currency) {
		boolean flag = false;
		for (WebElement webElement : countryList) {
			if (webElement.getText().equals(currency)) {
				webElement.click();
				flag = true;
			}
		}
		if (!flag) {
			assertFalse(true, currency + "is Invalid name. Please Enter Valid Currency");

		}
	}

	public static WebDriver navigateToURL(WebDriver driver, String URL) {

		driver.get(URL);
		return driver;

	}
}