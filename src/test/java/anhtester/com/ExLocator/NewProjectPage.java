package anhtester.com.ExLocator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class NewProjectPage {
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

    public static void fillInfoNewProject() throws InterruptedException {
        driver.findElement(By.xpath("//aside//li[contains(normalize-space(),'Project')]")).click();
        driver.findElement(By.xpath("//a[normalize-space()='New Project']")).click();

        NewProjectPage page = new NewProjectPage();
        page.fillInput("Project Name","Bao");
        page.chooseSelect("Customer","123abc");
        page.chooseSelect("Billing Type","Fixed Rate");
        page.chooseSelect("Status","In Progress");
        page.fillInput("Total Rate","5");
        page.fillInput("Estimated Hours","180");
        page.chooseSelect("Members","Admin Anh Tester");
        page.selectDate("Start Date");
        page.chooseTag();

        driver.switchTo().frame("description_ifr");
        driver.findElement(By.xpath("//body[@id='tinymce']")).sendKeys("abcdef123456789");
    }

    public void fillInput(String inputName, String value){
        String locatorInputString = "//div[label[contains(normalize-space(),'%s')]]/input";
        locatorInputString = String.format(locatorInputString,inputName);
        WebElement locatorInput = driver.findElement(By.xpath(locatorInputString));
        locatorInput.sendKeys(value);
    }

    public void chooseSelect(String inputName, String value) throws InterruptedException {
        String locatorInputString = "//div[label[text()='%s']]//button";
        System.out.println("---------" + String.format(locatorInputString,inputName));
        driver.findElement(By.xpath(String.format(locatorInputString,inputName))).click();

//        String locatorSelectString = "//li[contains(normalize-space(),'%s')]";
        String locatorSelectString = "//li[a[span[text()='%s']]]";
        switch (inputName){
            case "Customer":
                Thread.sleep(3000);
                WebElement searchCustomerName = driver.findElement(By.xpath("//div[label[text()='Customer']]//input"));
                searchCustomerName.sendKeys(value);
                driver.findElement(By.xpath(String.format(locatorSelectString,value))).click();
                break;

            default:
                System.out.println("---------" + String.format(locatorSelectString,value));
                driver.findElement(By.xpath(String.format(locatorSelectString,value))).click();
                break;

        }
    }

    public void selectDate(String name) throws InterruptedException {
        String xpathCalendar = String.format("//div[label[contains(normalize-space(),'%s')]]//div[i]",name);
        WebElement calendarIcon = driver.findElement(By.xpath(xpathCalendar));
        calendarIcon.click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//td[contains(@class,'xdsoft_date')]//div[text()='20']")).click();
    }

    public void chooseTag() throws InterruptedException {
        WebElement tagLocate =  driver.findElement(By.xpath("//div[label[normalize-space()='Tags']]//li/input"));
        tagLocate.click();
        Thread.sleep(2000);
        WebElement firstTag =  driver.findElement(By.xpath(" //ul[@id='ui-id-1']/li[1]"));
        firstTag.click();

    }

    public static void main(String[] args) throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://crm.anhtester.com/admin/authentication");
        login();
        fillInfoNewProject();
    }
}
