package selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Test;
import org.junit.Before;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        System.out.println("Iniciando configuración...");
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://www.google.com");
        driver.manage().window().maximize();
        System.out.println(driver.getCurrentUrl());
        System.out.println(driver.getTitle());
    }

    @Test
    public void buscarEnGoogleDevOpsHandbookDevuelvePaginaDescripcion() {
        System.out.println("Iniciando el test de búsqueda de libro en Google");
        WebElement searchbox = driver.findElement(By.name("q"));
        searchbox.sendKeys("DevOps Handbook");
        searchbox.submit();

        // List<WebElement> elements = driver.findElements(By.tagName("a"));
        String urlDescripcionLibro = "";
        List<WebElement> elements = driver.findElements(By.xpath("//a[starts-with(@href, 'https://www.amazon.com/')]"));
        System.out.println("Number of elements:" + elements.size());

        for (int i = 0; i < elements.size(); i++) {
            System.out.println("Enlaces Href:" + elements.get(i).getAttribute("href"));
            if (elements.get(i).getAttribute("href").contains("DevOps")) {
                urlDescripcionLibro = elements.get(i).getAttribute("href").trim();
                break;
            }
        }
        System.out.println("Url Descripción:" + urlDescripcionLibro);
        if (!urlDescripcionLibro.isEmpty()) {
            driver.navigate().to(urlDescripcionLibro);
            assertTrue("Pagina de descripcion", driver.getTitle().contains("DevOps Handbook"));
            // assertEquals("DevOps Handbook - Buscar con Google", driver.getTitle());
        } else {

            assertEquals("DevOps Handbook - Buscar con Google", driver.getTitle());
        }

    }

    @Test
    public void buscarEnAmazonThePhoenixProjectkDevuelvePaginaDescripcion() {
        System.out.println("Iniciando el test de búsqueda de libro en Amazon");
        driver.navigate().to("https://www.amazon.com");
        WebElement searchbox = driver.findElement(By.name("field-keywords"));
        searchbox.sendKeys("The Phoenix Project");
        searchbox.submit();

        String urlDescripcionLibro = "";
        List<WebElement> elements = driver.findElements(By.xpath(
                "//a[starts-with(@class, 'a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal')]"));
        System.out.println("Number of elements:" + elements.size());

        for (int i = 0; i < elements.size(); i++) {
            System.out.println("Enlaces Href:" + elements.get(i).getAttribute("href"));
            System.out.println("Text:" + elements.get(i).getText());
            if (elements.get(i).getText().startsWith("The Phoenix Project: A Novel")) {
                urlDescripcionLibro = elements.get(i).getAttribute("href").trim();
                break;
            }
        }
        System.out.println("Url Descripción:" + urlDescripcionLibro);
        if (!urlDescripcionLibro.isEmpty()) {
            driver.navigate().to(urlDescripcionLibro);
            assertTrue("Pagina de descripcion", driver.getTitle().contains("The Phoenix Project"));

        } else {

            assertEquals("Amazon.com : The Phoenix Project", driver.getTitle());
        }
    }

}
