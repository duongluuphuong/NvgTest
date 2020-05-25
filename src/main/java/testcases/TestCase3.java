package testcases;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import helper.FindElementHelper;
import page.AdvanceSearchPage;
import page.LoginPage;

public class TestCase3 {
	private static WebDriver driver;
	public static void Test() throws Exception {

		System.setProperty("webdriver.chrome.driver", "src\\driver\\81\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.amazon.com/");
		
		// 3. Verify result list is paginated if there are more than 16 items
		/*
		 * a. Perform a search with: i. Department: Books ii. Keyword: apple iii. Book
		 * Language: English b. The Result displays exactly 16 items on each page.
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

		advancePageSearch.ClickSubmitButton();

		// Verify the pagination is displayed
		try {
			findElementHelper.FindElement(driver, By.xpath("//ul[@class='a-pagination']"));
		} catch (Exception ex) {
			throw new Exception("Cannot find the pagination on this page");
		}
		// Verify only 16 results on one page
		int numberOfResultPerPage = findElementHelper
				.FindElements(driver,
						By.xpath("//span[@data-component-type='s-search-results']/div/div/div[@class='sg-col-inner']"))
				.size();
		System.out.println("size list reuslt " + numberOfResultPerPage);
		if (numberOfResultPerPage != 16) {
			throw new Exception("The number of results on one page is not 16.");
		}
		driver.quit();
	}
}
