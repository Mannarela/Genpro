

Genpro Task - Saucedemo Automation 
1. Framework Used : TestNG
2. Why TestNG is used:
Numerous test cases can be organized with ease by translating them into the testng.xml file
Sets priorities on test case execution 
A test case can be executed at different times using the keyword invocationCount by assigning an iteration number to invocationCount variable
Supports cross-browser testing and supports tools like Maven, Jenkins etc.
Unlike WebDriver, TestNG has a built-in mechanism to generate reports in a readable format
Project Name:  Saucedemo
Class Files:
.\Saucedemo\src\test\java\saucedemo\GenproTask.java
.\Saucedemo\src\test\java\saucedemo\LoginPage.java
Resource Files:
.\Saucedemo\src\test\resources\LoginCredentials.xlsx
Report:
.\Saucedemo\test-output\index.xlsx

Test Scenarios in LoginPage.java
1. Validated Login page with valid Username and Password and Verify the Home Page Launch.
2. Validated Login page for the Locked user and Verify  the error Message Assertion.

Test Scenarios in GenproTask.java
1. Validate the URL of the Saucedemo website
2. Validate the Title of the website
3. Validate the User login in to the Application with Valid User
4. Validate the Price Filter range from Low to high 
5. Validate the Items adding in to Cart
6. Validate the Items View from the Cart
7. Validate the Items remove from the cart  where Price  is less than $15 
8. Validate the Checkout process by clicking Checkout button
9. Validate the Customer information Input Data with Empty Input
10. Validate the Customer information Input Data with Valid Input
11. Validate the Checkout Completion process
12. Validate that Navigate to the Home page Successfully by clicking back to Home Button
13. Validate the Logout 




