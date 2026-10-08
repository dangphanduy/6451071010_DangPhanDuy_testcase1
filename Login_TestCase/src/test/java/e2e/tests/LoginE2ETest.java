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
}