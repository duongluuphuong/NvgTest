package testcases;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;


import helper.FindElementHelper;
import page.AdvanceSearchPage;
import page.LoginPage;
import page.ResultPage;

public class TestCase4 {
	private static WebDriver driver;
	public static void Test() throws Exception {

		System.setProperty("webdriver.chrome.driver", "src\\driver\\81\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.amazon.com/");
		
		// 4. Verify result list can be sorted on demand
		/*
		 * a. Perform a search with: i. Department: Books ii. Keyword: apple iii. Book
		 * Language: English iv. Change sort option to Price: Low to High
		 * 
		 * b. The Result is sorted by Price from Low to High
		 */

		// Navigate to advance search page and search
		List<String> testData = Files.readAllLines(Paths.get("src/main/resources/testdata.txt"));
		driver.navigate().to(
				"https://www.amazon.com/ap/signin?openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.com%2F%3Fref_%3Dnav_signin&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.assoc_handle=usflex&openid.mode=checkid_setup&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&");
		
		LoginPage loginPage = new LoginPage(driver);
		FindElementHelper findElementHelper = new FindElementHelper();

		loginPage.Login(testData.get(0), testData.get(1));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to("https://www.amazon.com/Advanced-Search-Books/b/?ie=UTF8&node=241582011&ref_=sv_b_1");
		
		AdvanceSearchPage advancePageSearch = new AdvanceSearchPage(driver);
		advancePageSearch.InputKeyword("apple");

		advancePageSearch.SelectLanguage("English");

		advancePageSearch.SelectTypeOfSort("Price: Low to High");

		advancePageSearch.ClickSubmitButton();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		ResultPage resultPage = new ResultPage(driver);
		
		List<WebElement> listOfResult = resultPage.GetResults();
		List<Float> listPriceOriginal = new ArrayList<Float>();
		
		for (WebElement result : listOfResult) {
			try {
				WebElement ele = result.findElement(By.xpath(".//span[@class='a-price']/span[@class='a-offscreen']"));
				String priceString = ele.getAttribute("innerText").substring(1);

				float price = Float.parseFloat(priceString);

				listPriceOriginal.add(price);
			} catch (Exception e) {
				listPriceOriginal.add((float) -1);
			}
		}
		List<Float> listChange = new ArrayList<Float>(listPriceOriginal);
		Collections.sort(listChange);
		String error = "";
		for (int i = 0; i < listChange.size(); i++) {
			if (!listChange.get(i).equals(listPriceOriginal.get(i))) {
				error += "Element " + i + " is not equals." + "\n";
				error += "Actual item: " + listPriceOriginal.get(i) + "\n";
				error += "Expected item: " + listChange.get(i) + "\n";
			}
		}
		if (!error.equals("")) {
			throw new Exception(error);
		}
		driver.quit();
	}
}
