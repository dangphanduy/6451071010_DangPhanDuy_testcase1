# Login_TestCase — Kiểm thử E2E chức năng đăng nhập Văn phòng điện tử UTC

Bộ test tự động (Selenium + JUnit 5) cho trang đăng nhập
[https://vanphongdientu.utc.edu.vn/Login](https://vanphongdientu.utc.edu.vn/Login),
viết theo mô hình **Page Object Model**.

- Sinh viên: Đặng Phan Duy — MSSV 6451071010

## 1. Yêu cầu môi trường

| Thành phần | Phiên bản | Ghi chú |
|---|---|---|
| JDK | 25 | Kiểm tra bằng `java -version` |
| Google Chrome | Bản mới nhất | Selenium Manager tự tải ChromeDriver phù hợp, không cần cài tay |
| Gradle | Không cần cài | Dùng Gradle Wrapper có sẵn (`gradlew` / `gradlew.bat`, Gradle 9.6.0) |
| Internet | Bắt buộc | Lần đầu cần tải Gradle, thư viện và ChromeDriver; test chạy trên trang thật |

Thư viện chính (khai báo trong `Login_TestCase/build.gradle.kts`):
Selenium Java 4.25.0, JUnit Jupiter (BOM 6.0.0), AssertJ 3.26.0.

## 2. Cấu trúc thư mục

```
Login_TestCase/
├── build.gradle.kts                 # Cấu hình Gradle + thư viện
├── gradlew, gradlew.bat             # Gradle Wrapper
└── src/test/java/e2e/
    ├── base/BaseTest.java           # Mở/đóng Chrome trước/sau mỗi test, hỗ trợ HEADLESS
    ├── pages/
    │   ├── BasePage.java            # Explicit Wait + hàm tiện ích click/type/getText
    │   ├── LoginPage.java           # Locator + thao tác trên trang Login
    │   └── HomePage.java            # Trang sau khi đăng nhập thành công
    └── tests/LoginE2ETest.java      # Các test case TC01 → TC07
```

## 3. Danh sách test case

| Mã | Tên hàm test | Kịch bản | Kết quả mong đợi | Cần biến môi trường |
|---|---|---|---|---|
| TC01 | `login_whenInvalidCredentials_staysOnLoginPageWithError` | Sai cả tài khoản và mật khẩu (ngẫu nhiên) | Ở lại `/Login`, có thông báo lỗi | — |
| TC02 | `login_whenUsernameIsEmpty_staysOnLoginPageWithError` | Bỏ trống tên đăng nhập | Ở lại `/Login`, có thông báo lỗi | — |
| TC03 | `login_whenPasswordIsEmpty_staysOnLoginPageWithPasswordError` | Bỏ trống mật khẩu | Ở lại `/Login`, lỗi chứa "chưa nhập mật khẩu" | — |
| TC04 | `login_whenBothFieldsAreEmpty_staysOnLoginPageWithError` | Bỏ trống cả hai ô | Ở lại `/Login`, có thông báo lỗi | — |
| TC05 | `login_whenOnlySpaces_staysOnLoginPageWithInvalidCredentialsError` | Cả hai ô chỉ nhập khoảng trắng | Ở lại `/Login`, lỗi "tài khoản hoặc mật khẩu không đúng" | — |
| TC06 | `login_whenCorrectUsernameWrongPassword_staysOnLoginPageWithInvalidCredentialsError` | Đúng tài khoản, sai mật khẩu | Ở lại `/Login`, lỗi "tài khoản hoặc mật khẩu không đúng" | `UTC_USER` |
| TC07 | `login_whenWrongUsernameCorrectPassword_staysOnLoginPageWithInvalidCredentialsError` | Sai tài khoản, đúng mật khẩu | Ở lại `/Login`, lỗi "tài khoản hoặc mật khẩu không đúng" | `UTC_PASS` |

> TC06 và TC07 sẽ được **bỏ qua (SKIPPED)** nếu chưa đặt biến môi trường tương ứng —
> không làm hỏng cả bộ test.

## 4. Cách chạy test

Mọi lệnh đều chạy **trong thư mục `Login_TestCase`**:

```bash
cd Login_TestCase
```

### 4.1. Chạy toàn bộ test case

Windows (PowerShell / CMD):

```bash
.\gradlew.bat test
```

macOS / Linux / Git Bash:

```bash
./gradlew test
```

> Gradle sẽ coi task `test` là *UP-TO-DATE* nếu code không đổi và không chạy lại.
> Muốn ép chạy lại, thêm `--rerun`, ví dụ: `.\gradlew.bat test --rerun`.

### 4.2. Chạy một test case cụ thể

Dùng tham số `--tests` với tên lớp hoặc tên hàm. Ví dụ chạy riêng TC03:

```bash
.\gradlew.bat test --tests "e2e.tests.LoginE2ETest.login_whenPasswordIsEmpty_staysOnLoginPageWithPasswordError"
```

Chạy tất cả test trong lớp `LoginE2ETest`:

```bash
.\gradlew.bat test --tests "e2e.tests.LoginE2ETest"
```

Có thể dùng ký tự đại diện `*`, ví dụ các test có chữ `Empty` trong tên:

```bash
.\gradlew.bat test --tests "*Empty*"
```

### 4.3. Cung cấp tài khoản thật cho TC06, TC07

Tài khoản thật **không được viết vào code**; truyền qua biến môi trường trước khi chạy.

PowerShell:

```powershell
$env:UTC_USER = "ten_dang_nhap_cua_ban"
$env:UTC_PASS = "mat_khau_cua_ban"
.\gradlew.bat test --rerun
```

CMD:

```bat
set UTC_USER=ten_dang_nhap_cua_ban
set UTC_PASS=mat_khau_cua_ban
gradlew.bat test --rerun
```

macOS / Linux / Git Bash:

```bash
UTC_USER=ten_dang_nhap_cua_ban UTC_PASS=mat_khau_cua_ban ./gradlew test --rerun
```

> Lưu ý: TC06 và TC07 cố tình đăng nhập sai với tài khoản thật. Không nên chạy lặp lại
> quá nhiều lần liên tiếp để tránh hệ thống khoá tài khoản.

### 4.4. Chạy ở chế độ headless (không hiện cửa sổ Chrome)

Mặc định Chrome sẽ mở cửa sổ (phóng to toàn màn hình). Đặt `HEADLESS=true` để chạy ngầm
(cửa sổ ảo 1920×1080), phù hợp khi chạy trên CI/server:

PowerShell:

```powershell
$env:HEADLESS = "true"
.\gradlew.bat test --rerun
```

macOS / Linux / Git Bash:

```bash
HEADLESS=true ./gradlew test --rerun
```

### 4.5. Chạy bằng IntelliJ IDEA

1. Mở thư mục `Login_TestCase` bằng IntelliJ IDEA, chờ Gradle đồng bộ xong.
2. Mở `src/test/java/e2e/tests/LoginE2ETest.java`.
3. Bấm biểu tượng ▶ cạnh tên lớp (chạy tất cả) hoặc cạnh từng hàm `@Test` (chạy một test case).
4. Muốn truyền `UTC_USER`, `UTC_PASS`, `HEADLESS`: vào **Run → Edit Configurations…**,
   chọn cấu hình test và điền vào mục **Environment variables**.

## 5. Xem kết quả

- Trên terminal: mỗi test in ra trạng thái `PASSED` / `FAILED` / `SKIPPED`.
- Báo cáo HTML chi tiết: mở file

  ```
  Login_TestCase/build/reports/tests/test/index.html
  ```

- Kết quả dạng XML (dùng cho CI): `Login_TestCase/build/test-results/test/`.

## 6. Lỗi thường gặp

| Hiện tượng | Nguyên nhân / Cách xử lý |
|---|---|
| `Unsupported class file major version` hoặc lỗi biên dịch | Chưa dùng JDK 25. Cài JDK 25 và đặt `JAVA_HOME` trỏ tới nó |
| `SessionNotCreatedException` | Chrome chưa cài hoặc quá cũ; cập nhật Chrome. Kiểm tra kết nối mạng để Selenium Manager tải ChromeDriver |
| `TimeoutException` khi chờ phần tử | Mạng chậm hoặc trang UTC đang lỗi/bảo trì; thử mở trang bằng tay rồi chạy lại |
| TC06/TC07 báo `SKIPPED` | Chưa đặt biến môi trường `UTC_USER` / `UTC_PASS` (xem mục 4.3) |
| Chạy lệnh nhưng không có test nào chạy lại | Task đang *UP-TO-DATE*; thêm `--rerun` |
