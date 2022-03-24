package selenium.Models;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import selenium.Utilidades.Utils;

public class FormularioRegistrarCuenta {
	
	private WebDriver driver;
	
	public FormularioRegistrarCuenta(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebElement getSignUpForm() {
		return Utils.waitForElementPresence(driver, By.id("create-account_form"), 30);
	}
	
	public WebElement getSignUpEmailField() {
		return Utils.waitForElementPresence(driver, By.id("email_create"), 30);
	}
	
	public WebElement getSignUpBtn() {
		return Utils.waitForElementPresence(driver, By.id("SubmitCreate"), 30);
	}
	
	
	public void setEmailField(String mail) {
		WebElement email = this.getSignUpEmailField();
		email.clear();
		email.sendKeys(mail);
	}
	
	
	
}
