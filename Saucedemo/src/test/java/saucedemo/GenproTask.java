package saucedemo;
import java.time.Duration;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;


public class GenproTask {
	
	WebDriver driver;
	
	@BeforeTest
	public void setup()
	{		
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.navigate().to("https://www.saucedemo.com/");
	}	
	
	@Test(priority = 0)
	public void verifytitle()
	{
		String expectedtitle="Swag Labs";
		String actualtitle=driver.getTitle();
		Assert.assertEquals(actualtitle, expectedtitle);
		System.out.println(actualtitle);
	}
	
	@Test(priority = 1)
	public void verifyurl() 
	{
		String expectedurl="https://www.saucedemo.com/";
		String actualurl=driver.getCurrentUrl();
		Assert.assertEquals(actualurl, expectedurl);
		System.out.println(actualurl);
	}

	@Test(priority = 2)
	public void loginByUser()
	{
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
		String expectedHeading="Swag Labs";
		WebElement pageHeading=driver.findElement(By.xpath("//div[@class='app_logo']"));
		String actualHeading= pageHeading.getText();
		Assert.assertEquals(actualHeading, expectedHeading);
		System.out.println("Validated Home page Successsfully with Page Heading: "+ actualHeading);
	}
	
	@Test(priority = 3)
	public void priceFilter()
	{
		WebElement filterdropdown = driver.findElement(By.xpath("//select[@class='product_sort_container']"));
		filterdropdown.click();		
		Select sel = new Select(filterdropdown);
		sel.selectByVisibleText("Price (low to high)");
	}
	
	@Test(priority = 4)
	public void addingItemsToCart() throws InterruptedException
	{
		List<WebElement> items = driver.findElements(By.xpath("//div[@class='inventory_item_name']"));			  
		for (WebElement webElement : items) {
			System.out.println(webElement.getText());
			webElement.findElement(By.xpath("//div/button[text()='Add to cart']")).click();
		}	
	}
	
	@Test(priority = 5)
	public void viewcartList() throws InterruptedException
	{
		driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).click();	
	}
	
	@Test(priority = 6)
	public void removeCartItems() throws InterruptedException
	{
		List<WebElement> CartItems = driver.findElements(By.xpath("//div[@class='inventory_item_name']"));
		  int noOfItemsBeforeRemoving = CartItems.size();		
		 for (WebElement webElement : CartItems) {
			WebElement priceElement = webElement.findElement(By.xpath("//div[@Class='inventory_item_price']"));
			String priceText = priceElement.getText();			
			double price = Double.parseDouble(priceText.replace("$", ""));			  
			  if (price < 15.0) {
				  WebElement removeButton = webElement.findElement(By.xpath("//button[text()='Remove']"));
				  removeButton.click();  
				  }
		}		
		List<WebElement> CartItemsAfterRemoved = driver.findElements(By.xpath("//div[@class='inventory_item_name']"));
		int afterremovingtheItems = CartItemsAfterRemoved.size();		
		if(noOfItemsBeforeRemoving == afterremovingtheItems) {
			System.out.println("THE ITEMS lESS THAN 15$ IS NOT REMOVED");
		}else {
			System.out.println("THE ITEMS lESS THAN 15$ IS REMOVED");
		}
	}	

	@Test(priority = 7)
	public void checkout() throws InterruptedException
	{
		driver.findElement(By.id("checkout")).click();	
	}
	@Test(priority = 8)
	public void customerInfo_EmptyDataValidation() throws InterruptedException
	{
		driver.findElement(By.id("first-name")).sendKeys("");
		driver.findElement(By.id("last-name")).sendKeys("");
		driver.findElement(By.id("postal-code")).sendKeys("");
		driver.findElement(By.id("continue")).click();
		WebElement Firstname_error_msg= driver.findElement(By.xpath("//*[text()='Error: First Name is required']"));
		String FirstnameError= Firstname_error_msg.getText();
		Assert.assertEquals(FirstnameError, "Error: First Name is required");
		System.out.println("Empty FirstName Assertion completed");	
	}
	
	@Test(priority = 9)
	public void customerInfo_validData() throws InterruptedException
	{
		driver.findElement(By.id("first-name")).sendKeys("Mannar");
		driver.findElement(By.id("last-name")).sendKeys("Mannan");
		driver.findElement(By.id("postal-code")).sendKeys("601201");
		driver.findElement(By.id("continue")).click();	
	}	
	
	@Test(priority = 10)
	public void checkoutCompletedValidation()
	{
		driver.findElement(By.id("finish")).click();
		WebElement completepage= driver.findElement(By.xpath("//*[text()='Thank you for your order!']"));
		String Finishpage= completepage.getText();
		Assert.assertEquals(Finishpage, "Thank you for your order!");
		System.out.println("Checkout completed Successfully");
	}	

	@Test(priority = 11)
	public void backToHomePage() throws InterruptedException
	{
		driver.findElement(By.id("back-to-products")).click();
		WebElement Homepage= driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span"));
		String Home= Homepage.getText();
		Assert.assertEquals(Home, "Products");
		System.out.println("Navigated to Home Page Successfully");
		Thread.sleep(3000);
	}
	
	@Test(priority = 12)
	public void logout() throws InterruptedException
	{	
		driver.findElement(By.xpath("//*[@class='bm-burger-button']")).click();		
	    driver.findElement(By.xpath("//a[@id='logout_sidebar_link']")).click();		
		WebElement loginpage= driver.findElement(By.id("login-button"));
		String login= loginpage.getAttribute("value");
		Assert.assertEquals(login, "Login");
		System.out.println("Navigated to Login Page Successfully");
	}
		
	@AfterTest
	public void closeBrowser()
	{
		driver.close();
	}
	
}
