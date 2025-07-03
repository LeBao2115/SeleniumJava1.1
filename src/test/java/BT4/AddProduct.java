package BT4;

import BT3.AddCategory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class AddProduct extends AddCategory {

    @BeforeTest
    public void setUp() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get("https://cms.anhtester.com/login");
        wait = new WebDriverWait(driver, Duration.ofSeconds(35));
        login();
        TestAddCategory();
    }

    @Test
    public void TestAddProduct() throws InterruptedException {
        // Move to Add new Product page
        driver.findElement(By.xpath("//div[@class=\"aiz-side-nav-wrap\"]//li/a/span[text()='Products']")).click();
        driver.findElement(By.xpath("//a[@href='https://cms.anhtester.com/admin/products/create']")).click();
        String titleCategoryPage = driver.findElement(By.xpath("//h5")).getText().trim();
        Assert.assertEquals(titleCategoryPage,"Add New Product");

        intputString("name","random guy");
//        chooseSelect("Category",3);
        chooseCategory();
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.TAB).sendKeys(Keys.TAB).perform();
        intputStringWithAction("KG");
        actions.sendKeys(Keys.TAB).sendKeys(Keys.TAB).perform();
        intputStringWithAction("2");
        actions.sendKeys(Keys.TAB).perform();
        intputStringWithAction("random guy say hi");

        chooseColor();

        intputString("unit_price","001");
//        intputString("qty_Aqua","300");
//        intputString("sku_Aqua","AZ3201");
        WebElement SaveAndPublicBtn = driver.findElement(By.xpath("//button[@class=\"btn btn-success action-btn\"]"));
        Actions actions1 = new Actions(driver);
        actions1.moveToElement(SaveAndPublicBtn).click().perform();
    }

    public void login(){
        // input email
        WebElement inputEmail = driver.findElement(By.id("email"));
        inputEmail.sendKeys("admin@example.com");
        //input password
        WebElement inputPassword = driver.findElement(By.id("password"));
        inputPassword.sendKeys("123456");
        //click Login btn
        WebElement loginBtn = driver.findElement(By.xpath("//button"));
        loginBtn.click();
    }

    public void intputString(String nameAttribute, String value){
        String inputStringLocator = String.format("//input[@name='%s']",nameAttribute);
        WebElement inputString = driver.findElement(By.xpath(inputStringLocator));
        inputString.sendKeys(value);
    }

    public void intputStringWithAction(String value){
        Actions actions = new Actions(driver);
        actions.sendKeys(value)
                .perform();
    }

    public void chooseSelect(String title,int index){
        String selectIndex = String.valueOf(index);
        String btnDropdownLocator = String.format("//label[contains(normalize-space(),'%s')]//following-sibling::div/div",title);
        WebElement btnDropdown = driver.findElement(By.xpath(btnDropdownLocator));
        btnDropdown.click();
        String chooseValue = btnDropdownLocator + "//following-sibling::div//li[" + selectIndex + "]";
        driver.findElement(By.xpath(chooseValue)).click();
    }

    public void chooseCategory(){
        String btnDropdownLocator = String.format("//label[contains(normalize-space(),'%s')]//following-sibling::div/div","Category");
        WebElement btnDropdown = driver.findElement(By.xpath(btnDropdownLocator));
        btnDropdown.click();
        driver.findElement(By.xpath("//label[contains(normalize-space(),'Category')]//following-sibling::div//input")).sendKeys(name);
        driver.findElement(By.xpath("//div[@id=\"bs-select-1\"]/ul/li[1]/a")).click();
    }

    public void chooseColor() throws InterruptedException {
        String divInputColor = "//div[input[@value='Colors']]";
        String switchBtn = divInputColor + "/following-sibling::div[2]";
        String openSelectColor = divInputColor + "/following-sibling::div[1]";
        //click switch button of color
        driver.findElement(By.xpath(switchBtn)).click();
        Thread.sleep(2000);

        //open select color
        driver.findElement(By.xpath(openSelectColor)).click();

        //choose colors
        String chooseColor = "//li/a[span[contains(normalize-space(),'%s')]]";
        driver.findElement(By.xpath(String.format(chooseColor,"Aqua"))).click();
        driver.findElement(By.xpath(String.format(chooseColor,"DarkRed"))).click();
        String text = driver.findElement(By.xpath(openSelectColor + "//button//div[@class='filter-option-inner-inner']")).getText();
        Assert.assertEquals(text,"2 items selected");
    }

}
