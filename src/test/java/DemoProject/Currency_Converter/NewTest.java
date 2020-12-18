package DemoProject.Currency_Converter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NewTest extends Util {
	WebDriver driver;
	public static String url = "https://transferwise.com/in/currency-converter/";

	@BeforeClass
	@Parameters({ "Browser" })
	public void setupDriver(String browser) {
		setupExtentReport(browser);

		logger.info("Executing Test Case on" + browser);

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\mudit.gaur\\Documents\\drivers\\chromedriver.exe");

			driver = new ChromeDriver();
		}

		else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "C:\\Users\\mudit.gaur\\Documents\\drivers\\geckodriver.exe");

			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("ie")) {

			System.setProperty("webdriver.ie.driver", "C:\\Users\\mudit.gaur\\Documents\\drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@DataProvider(name = "testData")
	public static Object[][] getTestDate() {
		return new Object[][] { { "100", "EUR", "GBP" }

				, { "200.2", "USD", "CAD" }, { "300.8", "EUR", "INR" }, { "1000", "AUD", "HKD" },
				{ "300.8", "SDG", "AFN" } };
	}

	@Test(dataProvider = "testData")
	public void CurrencyConversionTest(String cAmount, String fCurrency, String tCurrency) {
		logger.info("Converting " + cAmount + fCurrency + " To " + tCurrency);
		System.out.println("Converting " + cAmount + fCurrency + " To " + tCurrency);
		Util.navigateToURL(driver, url);
		logger.info("Navigating tp URL" + url);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(PageObjects.ddButton_Currency(driver, "source")));
		PageObjects.textBox_Amount(driver).clear();
		PageObjects.textBox_Amount(driver).sendKeys(cAmount);

		// value is source for "From Currency"
		PageObjects.ddButton_Currency(driver, "source").click();
		List<WebElement> countryList = PageObjects.ddList_Currency(driver, "source");

		Util.selectCountryFromDropdown(countryList, fCurrency);

		// value is target for "To Currency"
		PageObjects.ddButton_Currency(driver, "target").click();

		List<WebElement> targetcountryList = PageObjects.ddList_Currency(driver, "target");

		Util.selectCountryFromDropdown(targetcountryList, tCurrency);

		PageObjects.button_Convert(driver).click();

		// Taking the last value of span list which is current Conversion value
		List<WebElement> coversionValue = PageObjects.ddList_ConversionValue(driver);

		String currentConversionRate = coversionValue.get(coversionValue.size() - 1).getText();

		Double currentExchangeRate = Double.valueOf(currentConversionRate.split(" ")[0]);

		Double convertedcAmount = Double.valueOf(cAmount);

		Double expectedConversionValue = Double.valueOf(currentExchangeRate * convertedcAmount);

		Double actualConversionVaue = Double
				.valueOf(PageObjects.textBox_ActualConvertedValue(driver).getAttribute("value"));

		// Verify from Amount is same as entered on Page 1
		Double cAmountFrom = Double.valueOf(PageObjects.textBox_AmountForConversion(driver).getAttribute("value"));
		Assert.assertEquals(cAmountFrom, convertedcAmount, "Value same as passed");

		// Verify Converted Currency Amount
		Assert.assertEquals(actualConversionVaue, expectedConversionValue,
				"Actual Value" + actualConversionVaue + "same as passed" + expectedConversionValue);
		logger.info("expectedConversionValue " + convertedcAmount + fCurrency + " Equals " + actualConversionVaue
				+ tCurrency);
	}

	@AfterClass
	public void closeBrowser() {
		driver.close();
		report.flush();

	}

}
