package e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object cho trang dang nhap Van phong dien tu UTC.
 * - Locator de private.
 * - KHONG chua assertion.
 */
public class LoginPage extends BasePage {

    public static final String URL = "https://vanphongdientu.utc.edu.vn/Login";

    // ----- Locators (private) -----
    private final By usernameField = By.name("username");
    private final By passwordField = By.name("userpwd");
    private final By loginButton   = By.cssSelector("input.submit_login");
    private final By errorMessage  = By.cssSelector("div.error");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // ----- Actions -----

    public LoginPage open() {
        driver.get(URL);
        return this;
    }

    /** Dang nhap THANH CONG: tra ve trang ke tiep (HomePage). */
    public HomePage loginAs(String username, String password) {
        submitCredentials(username, password);
        return new HomePage(driver);
    }

    /** Dang nhap THAT BAI: server tai lai trang Login nen van tra ve LoginPage. */
    public LoginPage loginExpectingFailure(String username, String password) {
        submitCredentials(username, password);
        return this;
    }

    // ----- Queries (cho test dung de assert) -----

    public boolean isOnLoginPage() {
        return driver.getCurrentUrl().contains("/Login");
    }

    /** Cho dong loi hien ra roi tra ve noi dung (vd: "Ban chua nhap mat khau"). */
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    /** Cho toi khi trinh duyet roi khoi trang /Login (dung sau khi dang nhap dung). */
    public void waitUntilLeavesLoginPage() {
        waitUntilUrlNotContains("/Login");
    }

    // ----- Private helper -----

    private void submitCredentials(String username, String password) {
        type(usernameField, username);
        // De trong mat khau: bo qua buoc go, tranh sendKeys("") khong can thiet
        if (password != null && !password.isEmpty()) {
            type(passwordField, password);
        }
        click(loginButton);
    }
}