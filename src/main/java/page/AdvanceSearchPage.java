package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import helper.FindElementHelper;

public class AdvanceSearchPage {
	WebDriver driver;

	@FindBy(xpath = "//*[@id='asMiddle']//input[@name='field-keywords']")
	WebElement TxtKeyword;
	
	@FindBy(xpath = "//*[@id='asMiddle']//select[@name='field-language']")
	WebElement DdlLanguage;
	
	@FindBy(xpath = "//*[@id='asMiddle']//select[@name='sort']")
	WebElement DdlOrder;
	
	@FindBy(xpath = "//*[@id='asMiddle']//*[@name='Adv-Srch-Books-Submit']")
	WebElement BtnSubmit;
	
	public AdvanceSearchPage(WebDriver driver) {
		this.driver = driver;
		 PageFactory.initElements(driver, this);
	}
	
	public void InputKeyword(String keyword) {
		TxtKeyword.sendKeys(keyword);
	}
	
	public void SelectLanguage(String language) {
		new Select(DdlLanguage).selectByVisibleText(language);
	}
	
	public void SelectTypeOfSort(String sort) {
		new Select(DdlOrder).selectByVisibleText(sort);
	}
	
	public void ClickSubmitButton() {
		BtnSubmit.click();
	}
}
