package com;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

public class Test {

	private static WebDriver driver = null;

	public static void main(String[] args) throws Exception {

		TestCase3();

	}

	private static void TestCase1() throws Exception {
		StartWeb();
		// 1. Verify functionality of login with invalid account.
		List<String> testData = Files.readAllLines(Paths.get("src/main/resources/testdata.txt"));
		Login(testData.get(0), "123");
		try {
			FindElement(driver, By.cssSelector("#auth-error-message-box .a-box-inner.a-alert-container"));
			FindElement(driver, By.xpath("//h4[text()='There was a problem']"));
			FindElement(driver, By.xpath("//span[contains(text(),'Your password is incorrect')]"));
		} catch (Exception ex) {
			throw new Exception("Cannot find the alert on this page");
		}
	}

	private static void TestCase2() throws IOException {
		StartWeb();
		// 2. Verify user can login to amazon with a valid account.
		List<String> testData = Files.readAllLines(Paths.get("src/main/resources/testdata.txt"));
		Login(testData.get(0), testData.get(1));

	}

	private static void TestCase3() throws Exception {
		StartWeb();
		// 3. Verify result list is paginated if there are more than 16 items
		/*
		 * a. Perform a search with: i. Department: Books ii. Keyword: apple iii. Book
		 * Language: English b. The Result displays exactly 16 items on each page.
		 */
		// Navigate to advance search page and search
		List<String> testData = Files.readAllLines(Paths.get("src/main/resources/testdata.txt"));
		Login(testData.get(0), testData.get(1));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to("https://www.amazon.com/Advanced-Search-Books/b/?ie=UTF8&node=241582011&ref_=sv_b_1");
		FindElement(driver, By.xpath("//*[@id='asMiddle']//input[@name='field-keywords']")).sendKeys("apple");
		new Select(FindElement(driver, By.xpath("//*[@id='asMiddle']//select[@name='field-language']")))
				.selectByValue("English");
		FindElement(driver, By.xpath("//*[@id='asMiddle']//*[@name='Adv-Srch-Books-Submit']")).click();

		// Verify the pagination is displayed
		try {
			FindElement(driver, By.xpath("//ul[@class='a-pagination']"));
		} catch (Exception ex) {
			throw new Exception("Cannot find the pagination on this page");
		}

		// Verify only 16 results on one page
		int numberOfResultPerPage = FindElements(driver,
				By.xpath("//span[@data-component-type='s-search-results']/div/div/div[@class='sg-col-inner']")).size();
		System.out.println("size list reuslt "+numberOfResultPerPage);
		if (numberOfResultPerPage != 16) {
			throw new Exception("The number of results on one page is not 16.");
		}
	}

	private static void TestCase4() throws Exception {
		StartWeb();
		// 4. Verify result list can be sorted on demand
		/*
		 * a. Perform a search with: i. Department: Books ii. Keyword: apple iii. Book
		 * Language: English iv. Change sort option to Price: Low to High
		 * 
		 * b. The Result is sorted by Price from Low to High
		 */
		// Navigate to advance search page and search
		List<String> testData = Files.readAllLines(Paths.get("src/main/resources/testdata.txt"));
		// Login(testData.get(0), testData.get(1));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to("https://www.amazon.com/Advanced-Search-Books/b/?ie=UTF8&node=241582011&ref_=sv_b_1");
		FindElement(driver, By.xpath("//*[@id='asMiddle']//input[@name='field-keywords']")).sendKeys("apple");
		new Select(FindElement(driver, By.xpath("//*[@id='asMiddle']//select[@name='field-language']")))
				.selectByVisibleText("English");
		new Select(FindElement(driver, By.xpath("//*[@id='asMiddle']//select[@name='sort']")))
				.selectByVisibleText("Price: Low to High");
		FindElement(driver, By.xpath("//*[@id='asMiddle']//*[@name='Adv-Srch-Books-Submit']")).click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		List<WebElement> listOfResult = FindElements(driver, By.xpath(
				"//span[@data-component-type='s-search-results']//div[@class='a-section a-spacing-none a-spacing-top-small']"));
		System.out.println("size list reuslt "+listOfResult.size());

		List<Float> listPriceOriginal = new ArrayList<Float>();
		for (WebElement result : listOfResult) {
			// try {
			WebElement ele = result.findElement(By.xpath(".//span[@class='a-price']/span[@class='a-offscreen']"));
			String priceString = ele.getText().substring(1);

			float price = Float.parseFloat(priceString);

			listPriceOriginal.add(price);
//			} catch (Exception e) {
//				System.out.println(e.toString());
//				listPriceOriginal.add((float) 0.00);
//			}
		}
		System.out.println("list original" + listPriceOriginal);
		for (float pri : listPriceOriginal) {
			System.out.println("pri original" + pri);
		}
	}

	private static void StartWeb() {
		System.setProperty("webdriver.chrome.driver", "src\\driver\\81\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.amazon.com/");
	}

	private static void Login(String username, String password) {
		// navigate to login page, input username and password
		// after that click login
		driver.navigate().to(
				"https://www.amazon.com/ap/signin?openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.com%2F%3Fref_%3Dnav_signin&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.assoc_handle=usflex&openid.mode=checkid_setup&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&");

		FindElement(driver, By.cssSelector("#ap_email")).sendKeys(username);

		FindElement(driver, By.cssSelector("#continue")).click();

		FindElement(driver, By.cssSelector("#ap_password")).sendKeys(password);

		FindElement(driver, By.cssSelector("#signInSubmit")).click();
	}

	private static WebElement FindElement(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(by));
		actions.perform();
		return driver.findElement(by);
	}

	private static List<WebElement> FindElements(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		return driver.findElements(by);
	}
}
