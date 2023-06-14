package saucedemo;
import java.time.Duration;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginPage {
	
WebDriver driver;
FileInputStream fis =null;

   public void readFromExcel(int rownum) throws IOException
   {		
		XSSFWorkbook workbook = new XSSFWorkbook(fis);		
		XSSFSheet sheet = workbook.getSheet("Login");
		XSSFRow celldata=sheet.getRow(rownum);
		String username= celldata.getCell(0).getStringCellValue();
		String password= celldata.getCell(1).getStringCellValue();			
		driver.findElement(By.id("user-name")).sendKeys(username);			
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("login-button")).click();	
		workbook.close();	   
   }   

	@BeforeMethod
	public void setup() throws IOException
	{		
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.navigate().to("https://www.saucedemo.com/");
		fis = new FileInputStream("C://Users//Vijayalakshmi S T//Genpro14jun//Genpro//Saucedemo//src//test//resources//LoginCredentials.xlsx");		
	}	
	
	@Test(priority = 1)
	public void loginPageValidUser() throws IOException
	{
		    readFromExcel(1);
			String expectedHeading="Swag Labs";
			WebElement pageHeading=driver.findElement(By.xpath("//div[@class='app_logo']"));
			String actualHeading= pageHeading.getText();
			if(expectedHeading.equals(actualHeading))
			{
			System.out.println("Validated Home page Successsfully with Page Heading: "+ actualHeading);			
			}			
	}
	
	@Test(priority = 2)
	public void loginPageLockedUser() throws IOException
	{		
		    readFromExcel(2);
			WebElement error_msg= driver.findElement(By.xpath("//*[text()='Epic sadface: Sorry, this user has been locked out.']"));
			String LockedError= error_msg.getText();
			Assert.assertEquals(LockedError, "Epic sadface: Sorry, this user has been locked out.");
			System.out.println("Epic sadface: Sorry, this user has been locked out.");			
	}
	
	@AfterMethod
	public void closeBrowser()
	{
		driver.close();
	}

}
