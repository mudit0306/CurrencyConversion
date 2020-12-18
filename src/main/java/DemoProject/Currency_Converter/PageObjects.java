package DemoProject.Currency_Converter;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PageObjects {

	public static WebElement element = null;
	public static List<WebElement> elementList = null;

	public static WebElement textBox_Amount(WebDriver driver) {

		element = driver.findElement(By.id("cc-amount"));
		return element;

	}
	
	public static WebElement ddButton_Currency(WebDriver driver, String destination) {

		element = driver.findElement(By.xpath("//button[@data-id='"+destination+"Currency']"));
		return element;

	}
	
	public static List<WebElement> ddList_Currency(WebDriver driver, String destination) {

		elementList = driver.findElements(By.xpath("//button[@data-id='"+destination+"Currency']//following-sibling::div//li"));
		return elementList;

	}
	public static List<WebElement> ddList_ConversionValue(WebDriver driver) {

		elementList = driver
				.findElements(By.xpath("//h3[@class='cc__source-to-target m-t-2']//span"));
		return elementList;

	}
	
	public static WebElement button_Convert(WebDriver driver) {

		element = driver.findElement(By.id("convert"));
		return element;

	}
	
	public static WebElement textBox_ActualConvertedValue(WebDriver driver) {

		element = driver.findElement(By.id("cc-amount-to"));
		return element;

	}
	
	public static WebElement textBox_AmountForConversion(WebDriver driver) {

		element = driver.findElement(By.id("cc-amount-from"));
		return element;

	}
	
}
