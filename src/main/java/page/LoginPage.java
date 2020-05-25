package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import helper.FindElementHelper;

public class LoginPage {
	WebDriver driver;

	@FindBy(css = "#ap_email")
	WebElement TxtEmail;
	
	@FindBy(css = "#continue")
	WebElement BtnContinue;
	
	@FindBy(css = "#ap_password")
	WebElement TxtPassword;
	
	@FindBy(css = "#signInSubmit")
	WebElement BtnSubmit;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		 PageFactory.initElements(driver, this);
	}
	

	public void Login(String username, String password) {
		// navigate to login page, input username and password
		// after that click login

		TxtEmail.sendKeys(username);

		BtnContinue.click();

		TxtPassword.sendKeys(password);

		BtnSubmit.click();
	}
}
