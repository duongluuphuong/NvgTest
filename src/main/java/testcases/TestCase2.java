package testcases;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import helper.FindElementHelper;
import page.LoginPage;

public class TestCase2 {
	private static WebDriver driver;
	public static void Test() throws Exception {

		System.setProperty("webdriver.chrome.driver", "src\\driver\\81\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.amazon.com/");
		
		// 2. Verify user can login to amazon with a valid account.
		List<String> testData = Files.readAllLines(Paths.get("src/main/resources/testdata.txt"));
		driver.navigate().to(
				"https://www.amazon.com/ap/signin?openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.com%2F%3Fref_%3Dnav_signin&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.assoc_handle=usflex&openid.mode=checkid_setup&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.Login(testData.get(0), testData.get(1));
		try {
			FindElementHelper findElementHelper = new FindElementHelper();
			findElementHelper.FindElement(driver, By.cssSelector("#twotabsearchtextbox"));
			
		} catch (Exception ex) {
			throw new Exception("Cannot find the alert on this page");
		}finally {
			driver.quit();
		}
	}
}