package page;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {
	WebDriver driver;

	@FindAll({
			@FindBy(xpath = "//span[@data-component-type='s-search-results']//div[@class='a-section a-spacing-none a-spacing-top-small']") })
	List<WebElement> LstResults;

	public ResultPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public List<WebElement> GetResults() {
		return LstResults;
	}

}
