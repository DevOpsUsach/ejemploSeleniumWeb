package selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import selenium.Models.AccionesCompra;
import selenium.Models.Carrito;
import selenium.Models.CrearCuenta;
import selenium.Models.CuentaUsuario;
import selenium.Models.FormularioCrearCuenta;
import selenium.Models.FormularioInicioSesion;
import selenium.Models.FormularioRegistrarCuenta;
import selenium.Models.ResumenCarrito;
import selenium.Models.Ropas;
import selenium.Models.SeleccionarDireccion;

import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Before;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {

	private WebDriver driver;
	private Actions action;

	private Ropas clothes;
	private Carrito cart;
	private AccionesCompra shoppingActions;
	private ResumenCarrito summary;
	private FormularioInicioSesion signinForm;
	private CrearCuenta signupForm;
	private FormularioCrearCuenta registerForm;
	private SeleccionarDireccion seleccionarDireccion;
	private CuentaUsuario account;

	@Before
	public void setUp() {
		System.out.println("Iniciando configuración...");
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
		driver = new ChromeDriver();
		driver.get("http://automationpractice.com/index.php");
		driver.manage().window().maximize();

		action = new Actions(driver);

		clothes = new Ropas(driver);
		cart = new Carrito(driver);
		shoppingActions = new AccionesCompra(driver);
		signinForm = new FormularioInicioSesion(driver);
		signupForm = new CrearCuenta(driver);
		registerForm = new FormularioCrearCuenta(driver);
		seleccionarDireccion = new SeleccionarDireccion(driver);
		summary = new ResumenCarrito(driver);
		account = new CuentaUsuario(driver);

		/*
		 * System.out.println(driver.getCurrentUrl());
		 * System.out.println(driver.getTitle());
		 */

		/*
		 * 
		 * - Simular la navegación y compra de un producto
		 * - Seleccionar un producto. Agregar al carro
		 * - Al realizar checkout
		 * - Llenar el formulario con los datos
		 * - Crear una cuenta nueva
		 * - Verificar que el correo ingresado corresponda al formulario (Punto de
		 * verificación)
		 * - Address Billing (2 Punto de verificación. Los datos visualizados deben ser
		 * iguales a los datos ingresados)
		 * - Shipping Aceptar Terminos
		 * - Pay by bank wire
		 * - Confirmar orden (3 punto de verificación. Monto del Pay by bank wire. Total
		 * Ammount)
		 * -
		 * 
		 */
	}

	// @AfterClass
	// public void closeAll() {
	// //account.getAccountLogout().click();
	// driver.quit();
	// }

	@Test
	public void compraDeProducto() {
		System.out.println("Iniciando el test de Compra de producto en el sitio");
		String correo_registro = "testm4_10@usach.cl";

		Assert.assertTrue(clothes.getDressesBtn().isDisplayed());

		action.moveToElement(clothes.getDressesBtn()).perform();

		Assert.assertTrue(clothes.getSummerDressesBtn().isDisplayed());
		Assert.assertTrue(clothes.getCasualDressesBtn().isDisplayed());
		Assert.assertTrue(clothes.getEveningDressesBtn().isDisplayed());

		action.moveToElement(clothes.getSummerDressesBtn()).perform();
		clothes.getSummerDressesBtn().click();

		Assert.assertTrue(clothes.getSummerDressProduct(1).isDisplayed());
		Assert.assertTrue(clothes.getSummerDressProduct(2).isDisplayed());
		Assert.assertTrue(clothes.getSummerDressProduct(3).isDisplayed());
		Assert.assertEquals(clothes.getDressesCount().size(), 3);

		action.moveToElement(clothes.getSummerDressProduct(1)).perform();
		action.moveToElement(shoppingActions.getAddToCartBtn()).perform();

		Assert.assertTrue(shoppingActions.getAddToCartBtn().isDisplayed());

		action.click(shoppingActions.getAddToCartBtn()).build().perform();
		action.click(shoppingActions.getContinueShopingBtn()).build().perform();

		Assert.assertTrue(shoppingActions.getContinueShopingBtn().isDisplayed());

		action.moveToElement(cart.getCartTab()).perform();

		Assert.assertEquals(cart.getCartProductsQty().size(), 1);

		action.moveToElement(cart.getCartShipingCost()).perform();

		Assert.assertEquals(cart.getCartShipingCost().getText(), "$2.00");

		action.moveToElement(cart.getCartTotalPrice()).perform();

		Assert.assertEquals(cart.getCartTotalPrice().getText(), "$30.98");

		action.moveToElement(cart.getCartTab()).perform();
		action.moveToElement(cart.getCartTabCheckOutBtn()).perform();

		Assert.assertTrue(cart.getCartTabCheckOutBtn().isDisplayed());

		action.click(cart.getCartTabCheckOutBtn()).build().perform();

		Assert.assertTrue(summary.getCartSummaryTable().isDisplayed());
		Assert.assertEquals(summary.getCartSummTotalProductsNum().size(), 1);
		Assert.assertEquals(summary.getCartSummTotalProductsPrice().getText(), "$28.98");
		Assert.assertEquals(summary.getCartSummaryTotalPrice().getText(), "$30.98");
		Assert.assertEquals(summary.getCartSummTotalShipping().getText(), "$2.00");

		summary.getCartProceedBtn().click();

		Assert.assertTrue(signupForm.getCreateAccountForm().isDisplayed());

		signupForm.setCreateAccountEmailField(correo_registro);

		signupForm.getCreateAccountBtn().click();

		Assert.assertTrue(registerForm.getAccountCreationForm().isDisplayed());

		Assert.assertTrue(registerForm.getCustomerEmailField().isDisplayed());

		// Required fields filled
		Assert.assertEquals(registerForm.getCustomerEmailField().getAttribute("value"), correo_registro);

		registerForm.setCustomerFirstNameField("Especialista");
		registerForm.setCustomerLastNameField("DevOps");
		registerForm.setCustomerPasswordField("tallerM4");
		registerForm.selectCustomerDateOfBirthDay("11");
		registerForm.selectCustomerDateOfBirthMonth("2");
		registerForm.selectCustomerDateOfBirthYear("1985");
		registerForm.setAddressField("Mi Casa");
		registerForm.setCityField("Mi Ciudad");
		registerForm.selectState("32");
		registerForm.setPostalCodeField("21000");
		registerForm.setHomePhoneField("555555");
		registerForm.setMobilePhoneField("99999999");
		registerForm.setAddressAliasField("Mi Direccion");
		registerForm.getRegisterBtn().click();

		Assert.assertTrue(seleccionarDireccion.getSectionDeliveryAddress().isDisplayed());
		Assert.assertEquals(seleccionarDireccion.getCustomerName().getText(), "Especialista DevOps");
		Assert.assertEquals(seleccionarDireccion.getCustomerAddress().getText(), "Mi Casa");
		Assert.assertEquals(seleccionarDireccion.getCustomerCityAddress().getText(), "Mi Ciudad, New York 21000");
		Assert.assertEquals(seleccionarDireccion.getCustomerCountry().getText(), "United States");
		Assert.assertEquals(seleccionarDireccion.getCustomerHomePhone().getText(), "555555");
		Assert.assertEquals(seleccionarDireccion.getCustomerMobilePhone().getText(), "99999999");

		seleccionarDireccion.getProceedCheckoutBtn().click();

		Assert.assertTrue(summary.getFormShippingOptions().isDisplayed());

		summary.getCartSummTermsOfServiceCheck().click();
		summary.getCartProceedBtnTwo().click();

		Assert.assertTrue(summary.getCartSummaryTable().isDisplayed());

		summary.getCartSummPayByBankWire().click();

		Assert.assertEquals(summary.getCartSummPayByBankWireConfirm().getText(), "BANK-WIRE PAYMENT.");

		summary.getCartSummConfirmOrderBtn().click();

		Assert.assertTrue(summary.getOrderSuccessMsg().isDisplayed());
		// Assert.assertEquals(summary.getCartSummSuccessMsg().getText(), "Your order on
		// My Store is complete.");

	}

}
