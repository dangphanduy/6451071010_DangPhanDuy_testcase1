package e2e.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

/**
 * Lop cha cho moi test class.
 * - Mo mot trinh duyet Chrome moi, sach se truoc MOI test.
 * - Dong trinh duyet sau MOI test (ke ca khi test FAIL).
 * - Chay headless khi bien moi truong HEADLESS=true.
 */
public abstract class BaseTest {

    // static de cac lop khac (vd: ScreenshotWatcher) lay duoc driver qua getDriver()
    protected static WebDriver driver;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();

        if (isHeadless()) {
            options.addArguments("--headless=new");          // Chay khong hien cua so
            options.addArguments("--window-size=1920,1080"); // Co dinh kich thuoc man hinh
            options.addArguments("--no-sandbox");            // Can khi chay trong container/CI
            options.addArguments("--disable-dev-shm-usage");
        }

        // Selenium Manager tu tai ChromeDriver phu hop, khong can System.setProperty
        driver = new ChromeDriver(options);

        if (!isHeadless()) {
            driver.manage().window().maximize();
        }

        // Gioi han thoi gian tai trang; KHONG dung implicitlyWait
        // vi se xung dot voi Explicit Wait trong BasePage
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit(); // Dong Chrome va tat tien trinh ChromeDriver
            driver = null;
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    private static boolean isHeadless() {
        return Boolean.parseBoolean(System.getenv().getOrDefault("HEADLESS", "false"));
    }
}