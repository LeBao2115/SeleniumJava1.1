package anhtester.com.ExLocator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class NewCustomerPage {
    private static String email = "admin@example.com";
    private static String password = "123456";
    public static WebDriver driver;

    public static void login(){
        WebElement loginEmail = driver.findElement(By.id("email"));
        loginEmail.sendKeys(email);

        WebElement loginPassword = driver.findElement(By.name("password"));
        loginPassword.sendKeys(password);

        WebElement loginBtn = driver.findElement(By.xpath("//div/button"));
        loginBtn.click();
    }

    public static void navigateNewCustomerPage(){
        WebElement customerMenu = driver.findElement(By.xpath("//li[@class='menu-item-customers']/a"));
        customerMenu.click();

        WebElement newCustomerBtn = driver.findElement(By.xpath("//a[contains(normalize-space(),'New Customer')]"));
        newCustomerBtn.click();

        NewCustomerPage newCustomerPage = new NewCustomerPage();
        newCustomerPage.fillInput("company","TMA");
        newCustomerPage.fillInput("vat","123456789");
        newCustomerPage.fillInput("phonenumber","123456789");
        newCustomerPage.fillInput("website","https://crm.anhtester.com/admin/clients/client");
        newCustomerPage.selectOptionSelector("Groups","afk");
        newCustomerPage.selectOptionSelector("Currency","USD");
        newCustomerPage.selectOptionSelector("Default Language","German");
        newCustomerPage.fillInput("city","HCMC");
        newCustomerPage.fillInput("state","Binh Thanh");
        newCustomerPage.fillInput("zip","123456789");
        newCustomerPage.selectOptionSelector("Country","Vietnam");
        String address = "qqqqqqqqqqqwwwwwwwwwww";
        driver.findElement(By.xpath("//textarea[@id='address']")).sendKeys(address);
    }

    public void fillInput(String idName,String value){
        String inputCustomerDetail = "//input[@id='%s']";
        WebElement locatorInput = driver.findElement(By.xpath(String.format(inputCustomerDetail,idName)));
        locatorInput.sendKeys(value);
    }

    public void selectOptionSelector(String nameSelect,String value){
        String selectedBtnLocator = "//div[label[normalize-space()='%s']]//button[contains(@class,'btn dropdown-toggle')]";
        WebElement openSelectedBtn = driver.findElement(By.xpath(String.format(selectedBtnLocator,nameSelect)));
        openSelectedBtn.click();

        String pickValueString = "//div[@id='bs-select-%d']//li[contains(normalize-space(),'%s')]";
        switch (nameSelect){
            case "Groups":
                driver.findElement(By.xpath(String.format(pickValueString,1,value))).click();
                break;
            case "Currency":
                driver.findElement(By.xpath(String.format(pickValueString,2,value))).click();
                break;
            case "Default Language":
                driver.findElement(By.xpath(String.format(pickValueString,3,value))).click();
                break;
            case "Country":
                driver.findElement(By.xpath(String.format(pickValueString,4,value))).click();
                break;
        }

    }



    public static void main(String[] args) {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://crm.anhtester.com/admin/authentication");

        login();
        navigateNewCustomerPage();
    }
}
