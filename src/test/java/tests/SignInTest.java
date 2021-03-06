package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import page.objects.HomePage;
import page.objects.LoginPage;
import utilities.UserDataGenerator;

public class SignInTest extends BaseTest {

    @Test
    void shouldDisplayErrorMessageWhenNoPasswordProvided() {
        HomePage homePage = new HomePage();
        homePage.openPage();
        LoginPage loginPage = new LoginPage().goTo()
                .login(UserDataGenerator.emailGenerator(), "");
        Assert.assertTrue(loginPage.isPasswordRequiredAlertDisplayed());
    }

    @Test
    void shouldDisplayErrorMessageWhenNoEmailProvided() {
        HomePage homePage = new HomePage();
        homePage.openPage();
        LoginPage loginPage = new LoginPage().goTo().login("", UserDataGenerator.passwordGenerator());
        Assert.assertTrue(loginPage.isEmailRequiredAlertDisplayed());
    }

    @Test
    void shouldDisplayErrorMessageWhenNoEmailAndPasswordProvided() {
        HomePage homePage = new HomePage();
        homePage.openPage();
        LoginPage loginPage = new LoginPage().goTo().login("", "");
        Assert.assertTrue(loginPage.isPasswordRequiredAlertDisplayed() && loginPage.isEmailRequiredAlertDisplayed());
    }

    @Test
    void shouldDisplayErrorMessageWhenIncorrectEmailFormatProvided() {
        HomePage homePage = new HomePage();
        homePage.openPage();
        LoginPage loginPage = new LoginPage().goTo()
                .login("lalala", UserDataGenerator.passwordGenerator());
        Assert.assertTrue(loginPage.isIncorrectEmailFormatAlertDisplayed());
    }
}
