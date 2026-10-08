package e2e.pages;
 
import org.openqa.selenium.WebDriver;
 
/**
 * Trang sau khi dang nhap thanh cong.
 * Ban toi thieu: hien chi can constructor de LoginPage.loginAs() tra ve.
 * Sau nay co the bo sung locator (vd: ten nguoi dung, nut dang xuat).
 */
public class HomePage extends BasePage {
 
    public HomePage(WebDriver driver) {
        super(driver);
    }
 
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
} 