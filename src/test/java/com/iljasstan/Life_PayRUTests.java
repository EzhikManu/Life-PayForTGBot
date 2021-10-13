package com.iljasstan;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.iljasstan.Enums.CashRegistrs;
import com.iljasstan.Enums.TestDataEnum;
import config.CredentialsConfig;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selectors.byPartialLinkText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Life_PayRUTests {
    CredentialsConfig credentials = ConfigFactory.create(CredentialsConfig.class);
    String login = credentials.login();
    String password = credentials.password();
    @BeforeEach
    void beforeEach() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);

        Configuration.browserCapabilities = capabilities;
        Configuration.startMaximized = true;
        Configuration.remote = String.format("https://%s:%s@selenoid.autotests.cloud/wd/hub/", login, password);
    }
    void accessCookies() {
        if ($("#ok-write-cookie").isDisplayed()) {
        $("#ok-write-cookie").click();}
        else return;
    }
    @Test
    @DisplayName("Check the registration page with invalid tel and email")
    void registrationTest() {
        step("Open students account page", () -> {
                    open("https://my.life-pay.ru/?_ga=2.70394262.708319020.1634027069-1543519573.1634027069");
                });
        step("open registration page", () -> {
        $("#triggerRegister").click();
        });
        step("set number and email", () -> {
                    $("#phoneNum").click();
                    $("#phoneNum").setValue("1111111010");
                    $("#pass").setValue("gavipe2934@settags.com").pressEnter();
                });
        step("check the message about invalid values", () -> {
            $(".flash-0").shouldHave(Condition.text("Telephone number or email is invalid"));
        });
    }
    @EnumSource(value = TestDataEnum.class)
    @ParameterizedTest(name = "Check the values of the top lines")
    void topLineTest(TestDataEnum value) {
        step("open main page", () -> {
                    open("https://life-pay.ru");
                    accessCookies();
                });
        step("check the titles", () -> {
            $(".lf-header_topLineBottomNav").shouldHave(Condition.text(value.getDesc()));
        });
    }
    @Test
    @DisplayName("Check the consultation page is appeared")
    void getConsultationTest() {
        step("open main page", () -> {
                    open("https://life-pay.ru");
                    accessCookies();
                });
        step("click on the button 'получить консультацию'", () -> {
                    $(byPartialLinkText("Получить консультацию")).click();
                });
        step("check is consultation page appeared", () -> {
            assertTrue($("#request_form").isDisplayed());
        });
    }
    @EnumSource(value = CashRegistrs.class)
    @ParameterizedTest(name = "Check the list of cash registrs")
    void listOfCashRegistrsTest(CashRegistrs cashRegistr) {
        step("open main page", () -> {
                    open("https://life-pay.ru");
                    accessCookies();
                });
        step("click the button 'Подробнее' at the cash registers tab", () -> {
                    $(".lf-infoBlock2Column").$(byPartialLinkText("Подробнее")).click();
                });
        step("check is consultation page appeared", () -> {
            $("#item1").shouldHave(Condition.text(cashRegistr.getDesc()));
        });
    }
    @AfterEach
    void tearDown() {
        Attach.screenshotAs("Last screenshot");
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }
}
