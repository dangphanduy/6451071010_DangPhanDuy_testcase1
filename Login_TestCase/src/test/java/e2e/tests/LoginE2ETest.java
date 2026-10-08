package e2e.tests;

import e2e.base.BaseTest;
import e2e.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test E2E cho chuc nang dang nhap Van phong dien tu UTC.
 * File test chi chua nghiep vu + assertion, KHONG co selector.
 */
class LoginE2ETest extends BaseTest {

    @Test
    @DisplayName("TC01: Sai tai khoan hoac mat khau -> o lai trang Login va hien loi")
    void login_whenInvalidCredentials_staysOnLoginPageWithError() {
        // Arrange: du lieu ngau nhien, khong trung tai khoan that nao
        // (tranh viec dang nhap sai lien tuc lam khoa tai khoan that)
        String fakeUser = "sai_tai_khoan_" + System.currentTimeMillis();
        String fakePass = "SaiMatKhau@" + System.currentTimeMillis();

        // Act
        LoginPage loginPage = new LoginPage(driver).open();
        loginPage.loginExpectingFailure(fakeUser, fakePass);

        // Assert 1: co thong bao loi (getErrorMessage tu cho dong loi hien ra)
        String errorMessage = loginPage.getErrorMessage();
        assertThat(errorMessage)
                .as("Phai hien thong bao loi khi dang nhap sai")
                .isNotBlank();

        // Assert 2: van o lai trang Login
        assertThat(loginPage.isOnLoginPage())
                .as("Dang nhap sai thi phai o lai trang /Login")
                .isTrue();
    }

    @Test
    @DisplayName("TC02: Bo trong ten dang nhap -> o lai trang Login va hien loi")
    void login_whenUsernameIsEmpty_staysOnLoginPageWithError() {
        // Arrange: chi nhap mat khau ngau nhien, de trong ten dang nhap
        String fakePass = "MatKhau@" + System.currentTimeMillis();

        // Act
        LoginPage loginPage = new LoginPage(driver).open();
        loginPage.loginExpectingFailure("", fakePass);

        // Assert 1: co thong bao loi (getErrorMessage tu cho dong loi hien ra)
        String errorMessage = loginPage.getErrorMessage();
        assertThat(errorMessage)
                .as("Phai hien thong bao loi khi bo trong ten dang nhap")
                .isNotBlank();

        // Assert 2: van o lai trang Login
        assertThat(loginPage.isOnLoginPage())
                .as("Bo trong ten dang nhap thi phai o lai trang /Login")
                .isTrue();
    }

    @Test
    @DisplayName("TC03: Bo trong mat khau -> o lai trang Login va bao chua nhap mat khau")
    void login_whenPasswordIsEmpty_staysOnLoginPageWithPasswordError() {
        // Arrange: chi nhap ten dang nhap ngau nhien, de trong mat khau
        String fakeUser = "sai_tai_khoan_" + System.currentTimeMillis();

        // Act
        LoginPage loginPage = new LoginPage(driver).open();
        loginPage.loginExpectingFailure(fakeUser, "");

        // Assert 1: dong loi dung noi dung (getErrorMessage tu cho dong loi hien ra)
        String errorMessage = loginPage.getErrorMessage();
        assertThat(errorMessage)
                .as("Phai bao loi chua nhap mat khau")
                .containsIgnoringCase("chưa nhập mật khẩu");

        // Assert 2: van o lai trang Login
        assertThat(loginPage.isOnLoginPage())
                .as("Bo trong mat khau thi phai o lai trang /Login")
                .isTrue();
    }

    @Test
    @DisplayName("TC04: Bo trong ca ten dang nhap va mat khau -> o lai trang Login va hien loi")
    void login_whenBothFieldsAreEmpty_staysOnLoginPageWithError() {
        // Arrange: khong can du lieu, de trong ca hai o

        // Act
        LoginPage loginPage = new LoginPage(driver).open();
        loginPage.loginExpectingFailure("", "");

        // Assert 1: co thong bao loi (getErrorMessage tu cho dong loi hien ra)
        String errorMessage = loginPage.getErrorMessage();
        assertThat(errorMessage)
                .as("Phai hien thong bao loi khi bo trong ca hai o")
                .isNotBlank();

        // Assert 2: van o lai trang Login
        assertThat(loginPage.isOnLoginPage())
                .as("Bo trong ca hai o thi phai o lai trang /Login")
                .isTrue();
    }
}