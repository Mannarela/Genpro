package saucedemo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GenproTask {
	
	WebDriver driver;
	

	@BeforeTest(alwaysRun = true)
	public void setup()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Vijayalakshmi S T\\genpro\\Saucedemo\\chromedriver.exe");

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
	public void loginPageValidation()
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
	public void addingItems() throws InterruptedException
	{
	
		List<WebElement> items = driver.findElements(By.xpath("//div[@class='inventory_item_name']"));
			  
		for (WebElement webElement : items) {
			System.out.println(webElement.getText());
			webElement.findElement(By.xpath("//div/button[text()='Add to cart']")).click();
			Thread.sleep(2000);
		}	
	}
	
	@Test(priority = 5)
	public void cart() throws InterruptedException
	{
		driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).click();
		Thread.sleep(2000);	
	}
	
	@Test(priority = 6)
	public void removeCart() throws InterruptedException
	{
		List<WebElement> CartItems = driver.findElements(By.xpath("//div[@class='inventory_item_name']"));
		  int noOfItemsBeforeRemoving = CartItems.size();
		
		for (WebElement webElement : CartItems) {
			WebElement priceElement = webElement.findElement(By.xpath("//div[@Class='inventory_item_price']"));
			String priceText = priceElement.getText();
			System.out.println(priceText);
			
			  double price = Double.parseDouble(priceText.replace("$", ""));
			  
			  if (price < 15.0) {
				  WebElement removeButton = webElement.findElement(By.xpath("//button[text()='Remove']"));
				  removeButton.click(); Thread.sleep(2000);  
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
		Thread.sleep(2000);	
	}
	@Test(priority = 8)
	public void checkoutInfo_Invalid_FirstName() throws InterruptedException
	{
		//driver.findElement(By.id("first-name")).sendKeys(" ");
		driver.findElement(By.id("last-name")).sendKeys("Mannan");
		driver.findElement(By.id("postal-code")).sendKeys("601201");
		driver.findElement(By.id("continue")).click();
		Thread.sleep(2000);
		WebElement Firstname_error_msg= driver.findElement(By.xpath("//*[text()='Error: First Name is required']"));
		String FirstnameError= Firstname_error_msg.getText();
		Assert.assertEquals(FirstnameError, "Error: First Name is required");
		System.out.println("Empty FirstName Assertion completed");
		Thread.sleep(2000);	
	}

	@Test(priority = 9)
	public void checkoutInfo_Invalid_LastName() throws InterruptedException
	{
		driver.findElement(By.id("first-name")).sendKeys("Mannar");
		driver.findElement(By.id("last-name")).clear();
		driver.findElement(By.id("postal-code")).sendKeys("601201");
		driver.findElement(By.id("continue")).click();
		Thread.sleep(2000);
		WebElement Lastname_error_msg= driver.findElement(By.xpath("//*[text()='Error: Last Name is required']"));
		String LastnameError= Lastname_error_msg.getText();
		Assert.assertEquals(LastnameError, "Error: Last Name is required");
		System.out.println("Empty LastName Assertion completed");
		Thread.sleep(2000);	
	}
	
	@Test(priority = 10)
	public void checkoutInfo_Invalid_Postalcode() throws InterruptedException
	{
		driver.findElement(By.id("first-name")).sendKeys("Mannar");
		driver.findElement(By.id("last-name")).sendKeys("Mannan");
		driver.findElement(By.id("postal-code")).clear();
		driver.findElement(By.id("continue")).click();
		Thread.sleep(2000);
		WebElement Postalcode_error_msg= driver.findElement(By.xpath("//*[text()='Error: Postal Code is required']"));
		String PostalcodeError= Postalcode_error_msg.getText();
		Assert.assertEquals(PostalcodeError, "Error: Postal Code is required");
		System.out.println("Empty PostalCode Assertion completed");
		Thread.sleep(2000);	
	}
	
//	@Test(priority = 11)
//	public void checkoutInfo_valid() throws InterruptedException
//	{
//		driver.findElement(By.id("first-name")).sendKeys("Mannar");
//		driver.findElement(By.id("last-name")).sendKeys("Mannan");
//		driver.findElement(By.id("postal-code")).sendKeys("601201");
//		driver.findElement(By.id("continue")).click();
//		Thread.sleep(2000);	
//	}
	
	
	@AfterTest(alwaysRun =true)
	public void teardown()
	{
		//driver.close();
	}
	
}

