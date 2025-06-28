package BT1;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;

public class AddCategory {
    WebDriver driver;
    String name = RandomStringUtils.randomAlphabetic(5);
    String metaTitle = RandomStringUtils.randomAlphabetic(10);

    @BeforeTest
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://cms.anhtester.com/login");
    }

    @Test
    public void TestAddCategory() throws InterruptedException {
        // Login
        // input email
        WebElement inputEmail = driver.findElement(By.id("email"));
        inputEmail.sendKeys("admin@example.com");
        //input password
        WebElement inputPassword = driver.findElement(By.id("password"));
        inputPassword.sendKeys("123456");
        //click Login btn
        WebElement loginBtn = driver.findElement(By.xpath("//button"));
        loginBtn.click();

        //Move to category page
        driver.findElement(By.xpath("//div[@class=\"aiz-side-nav-wrap\"]//li/a/span[text()='Products']")).click();
        driver.findElement(By.xpath("//a[@href='https://cms.anhtester.com/admin/categories']")).click();

        // Navigate Add new category page
        WebElement addNewCategogyBtn = driver.findElement(By.xpath("//a/span[text()='Add New category']"));
        addNewCategogyBtn.click();
        chooseSelect("No Parent");
        chooseSelect("Physical");
        chooseSelect("Nothing selected");
        intputString("name",name);
        intputString("order_level","1");
        intputString("meta_title",metaTitle);
        chooseFile("banner");
        Thread.sleep(3000);
        chooseFile("icon");
        driver.findElement(By.xpath("//button[text()='Save']")).click();

        //find new category
        driver.findElement(By.xpath("//input[@id=\"search\"]")).sendKeys(name);
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ENTER)
                .perform();

//        String actualName = driver.findElements(By.xpath("//table/tbody/tr/td[2]")).get(0).getText();
//        String actualName = driver.findElement(By.xpath("//table/tbody/tr/td[2]")).getText();

        WebElement element = driver.findElement(By.xpath("//table/tbody/tr/td[2]"));
        actions.moveToElement(element).perform();
        String actualName = element.getText().trim();
        Assert.assertEquals(actualName,name);
    }

    public void chooseSelect(String title){
        String btnDropdownLocator = String.format("//button[@title='%s']",title);
        WebElement btnDropdown = driver.findElement(By.xpath(btnDropdownLocator));
        btnDropdown.click();
        String chooseValue = btnDropdownLocator + "//following-sibling::div//li[2]";
        driver.findElement(By.xpath(chooseValue)).click();
    }

    public void intputString(String nameAttribute, String value){
        String inputStringLocator = String.format("//input[@name='%s']",nameAttribute);
        WebElement inputString = driver.findElement(By.xpath(inputStringLocator));
        inputString.sendKeys(value);
    }

    public void chooseFile(String name){
        String chooseFileLocator = String.format("//input[@name='%s']/preceding-sibling::div[@class='form-control file-amount']",name);
        driver.findElement(By.xpath(chooseFileLocator)).click();
        driver.findElement(By.xpath("//div[@class=\"aiz-file-box-wrap\"][4]")).click();
        driver.findElement(By.xpath("//button[text()='Add Files']")).click();

    }
    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
