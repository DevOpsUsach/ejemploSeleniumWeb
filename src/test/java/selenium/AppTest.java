package selenium;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import selenium.Models.AccionesCompra;
import selenium.Models.Carrito;
import selenium.Models.CrearCuenta;
import selenium.Models.FormularioCrearCuenta;
import selenium.Models.ResumenCarrito;
import selenium.Models.Ropas;
import selenium.Models.SeleccionarDireccion;

/**
 * Unit test for simple App.
 */
public class AppTest {

	private WebDriver driver;
	private Actions action;

	private Ropas paginaRopas;
	private Carrito controladorCarrito;
	private AccionesCompra accionesCompra;
	private ResumenCarrito paginaResumen;	
	private CrearCuenta formularioRegistro;
	private FormularioCrearCuenta formularioCrearCuenta;
	private SeleccionarDireccion paginaSeleccionarDireccion;
	

	@Before
	public void setUp() {
		System.out.println("Iniciando configuración...");
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
		driver = new ChromeDriver();
		driver.get("http://automationpractice.com/index.php");
		driver.manage().window().maximize();

		action = new Actions(driver);

		paginaRopas = new Ropas(driver);
		controladorCarrito = new Carrito(driver);
		accionesCompra = new AccionesCompra(driver);		
		formularioRegistro = new CrearCuenta(driver);
		formularioCrearCuenta = new FormularioCrearCuenta(driver);
		paginaSeleccionarDireccion = new SeleccionarDireccion(driver);
		paginaResumen = new ResumenCarrito(driver);

	}

	
	
 
	@Test
	public void compraDeProducto() {
		System.out.println("Iniciando el test de Compra de producto en el sitio");
		String correo_registro = System.getProperty("correoRegistro", "someEmail@somedomain.com");

		// Selección de producto
		paginaRopas.getDressesBtn().isDisplayed();
		action.moveToElement(paginaRopas.getDressesBtn()).perform();
		paginaRopas.getSummerDressesBtn().isDisplayed();		
		action.moveToElement(paginaRopas.getSummerDressesBtn()).perform();
		paginaRopas.getSummerDressesBtn().click();
		action.moveToElement(paginaRopas.getSummerDressProduct(1)).perform();
		// Agregar al carrito

		action.moveToElement(accionesCompra.getAddToCartBtn()).perform();
		Assert.assertTrue(accionesCompra.getAddToCartBtn().isDisplayed());
		action.click(accionesCompra.getAddToCartBtn()).build().perform();
		action.click(accionesCompra.getContinueShopingBtn()).build().perform();
		Assert.assertTrue(accionesCompra.getContinueShopingBtn().isDisplayed());
		action.moveToElement(controladorCarrito.getCartTab()).perform();
		Assert.assertEquals(controladorCarrito.getCartProductsQty().size(), 1);
		action.moveToElement(controladorCarrito.getCartShipingCost()).perform();
		Assert.assertEquals(controladorCarrito.getCartShipingCost().getText(), "$2.00");
		action.moveToElement(controladorCarrito.getCartTotalPrice()).perform();
		Assert.assertEquals(controladorCarrito.getCartTotalPrice().getText(), "$30.98");
		action.moveToElement(controladorCarrito.getCartTab()).perform();
		String valorTotalCarrito = controladorCarrito.getCartTotalPrice().getText().trim();
		action.moveToElement(controladorCarrito.getCartTabCheckOutBtn()).perform();
		Assert.assertTrue(controladorCarrito.getCartTabCheckOutBtn().isDisplayed());
		action.click(controladorCarrito.getCartTabCheckOutBtn()).build().perform();


		// Comienzo del proceso de Checkout
		Assert.assertTrue(paginaResumen.getCartSummaryTable().isDisplayed());
		paginaResumen.getCartProceedBtn().click();
		Assert.assertTrue(formularioRegistro.getCreateAccountForm().isDisplayed());
		// Se provee una cuenta de correo para el registro
		formularioRegistro.setCreateAccountEmailField(correo_registro);
		formularioRegistro.getCreateAccountBtn().click();

		// Completar el fomulario de nuevo usuario
		Assert.assertTrue(formularioCrearCuenta.getAccountCreationForm().isDisplayed());
		Assert.assertTrue(formularioCrearCuenta.getCustomerEmailField().isDisplayed());
		// 1° Punto de verificación requerido para la actividad. 
		// Comprobar que el correo ingresado al iniciar el proceso de registro sea el mismo que aparece en el formulario de registro.
		Assert.assertEquals(formularioCrearCuenta.getCustomerEmailField().getAttribute("value"), correo_registro);
		formularioCrearCuenta.setCustomerFirstNameField("Especialista");
		formularioCrearCuenta.setCustomerLastNameField("DevOps");
		formularioCrearCuenta.setCustomerPasswordField("tallerM4");
		formularioCrearCuenta.selectCustomerDateOfBirthDay("11");
		formularioCrearCuenta.selectCustomerDateOfBirthMonth("2");
		formularioCrearCuenta.selectCustomerDateOfBirthYear("1985");
		formularioCrearCuenta.setAddressField("Mi Casa");
		formularioCrearCuenta.setCityField("Mi Ciudad");
		formularioCrearCuenta.selectState("32");
		formularioCrearCuenta.setPostalCodeField("21000");
		formularioCrearCuenta.setHomePhoneField("555555");
		formularioCrearCuenta.setMobilePhoneField("99999999");
		formularioCrearCuenta.setAddressAliasField("Mi Direccion");
		formularioCrearCuenta.getRegisterBtn().click();
		// Página de selección de dirección
		Assert.assertTrue(paginaSeleccionarDireccion.getSectionDeliveryAddress().isDisplayed());
		Assert.assertEquals(paginaSeleccionarDireccion.getCustomerName().getText(), "Especialista DevOps");
		// 2° Punto de verificación requerido para la actividad. 
		// Comprobar que la dirección sea la misma que se ingresó en el formulario de registro.
		Assert.assertEquals(paginaSeleccionarDireccion.getCustomerAddress().getText(), "Mi Casa");
		Assert.assertEquals(paginaSeleccionarDireccion.getCustomerCityAddress().getText(), "Mi Ciudad, New York 21000");		
		paginaSeleccionarDireccion.getProceedCheckoutBtn().click();

		// Página de método de envío y aceptar términos y condiciones
		Assert.assertTrue(paginaResumen.getFormShippingOptions().isDisplayed());
		paginaResumen.getCartSummTermsOfServiceCheck().click();
		paginaResumen.getCartProceedBtnTwo().click();

		// Página de resumen y método de pago.
		Assert.assertTrue(paginaResumen.getCartSummaryTable().isDisplayed());
		// 3° Punto de verificación requerido para la actividad. 
		// Comprobar que el monto total a pagar sea el mismo que aparecía en el carro.
		Assert.assertEquals(paginaResumen.getCartSummaryTotalPrice().getText(), valorTotalCarrito);
		paginaResumen.getCartSummPayByBankWire().click();
		Assert.assertEquals(paginaResumen.getCartSummPayByBankWireConfirm().getText(), "BANK-WIRE PAYMENT.");
		paginaResumen.getCartSummConfirmOrderBtn().click();

		// Página de orden completada.
		Assert.assertTrue(paginaResumen.getOrderSuccessMsg().isDisplayed());
		

	}

}
