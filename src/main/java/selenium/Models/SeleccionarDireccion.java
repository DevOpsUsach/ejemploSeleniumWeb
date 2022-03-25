package selenium.Models;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import selenium.Utilidades.Utils;

public class SeleccionarDireccion {

	private WebDriver driver;

	public SeleccionarDireccion(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getSectionDeliveryAddress() {
		return Utils.waitForElementPresence(driver, By.id("address_delivery"), 30);
	}

	public WebElement getCustomerName() {
		return Utils.waitForElementPresence(driver, By.cssSelector("li.address_firstname"), 30);
	}

	public WebElement getCustomerAddress() {
		return Utils.waitForElementPresence(driver, By.cssSelector("li.address_address1"), 30);
	}

	public WebElement getCustomerCityAddress() {
		return Utils.waitForElementPresence(driver, By.cssSelector("li.address_city"), 30);
	}

	public WebElement getCustomerCountry() {
		return Utils.waitForElementPresence(driver, By.cssSelector("li.address_country_name"), 30);
	}

	public WebElement getCustomerHomePhone() {
		return Utils.waitForElementPresence(driver, By.cssSelector("li.address_phone"), 30);
	}

	public WebElement getCustomerMobilePhone() {
		return Utils.waitForElementPresence(driver, By.cssSelector("li.address_phone_mobile"), 30);
	}

	public WebElement getProceedCheckoutBtn() {
		return Utils.waitToBeClickable(driver, By.xpath("//button[@name=\"processAddress\"]"), 30);
	}

}
