package com.iljasstan;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.iljasstan.Enums.TestDataEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static com.codeborne.selenide.Selectors.byPartialLinkText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Life_PayRUTests {
    @BeforeEach
    void beforeEach() {
        Configuration.startMaximized = true;
    }
    void accessCookies() {
        if ($("#ok-write-cookie").isDisplayed()) {
        $("#ok-write-cookie").click();}
        else return;
    }
    @Test
    void registrationTest() {
        /*open("https://life-pay.ru");
        accessCookies();
        $(".lf-header_topLineEnter").click();
        $(byText("LIFE PAY")).click();
        //closeWindow();
        forward();*/
        open("https://my.life-pay.ru/?_ga=2.70394262.708319020.1634027069-1543519573.1634027069");
        $("#triggerRegister").click();
        $("#phoneNum").click();
        $("#phoneNum").setValue("1111111010");
        $("#pass").setValue("gavipe2934@settags.com").pressEnter();

        $(".flash-0").shouldHave(Condition.text("Telephone number or email is invalid"));
    }
    @EnumSource(value = TestDataEnum.class)
    @ParameterizedTest(name = "Check the values of the top lines")
    void topLineTest(TestDataEnum value) {
        open("https://life-pay.ru");
            accessCookies();
        $(".lf-header_topLineBottomNav").shouldHave(Condition.text(value.getDesc()));
    }
    @Test
    void getConsultationTest() {
        open("https://life-pay.ru");
        accessCookies();
        $(byPartialLinkText("Получить консультацию")).click();
        assertTrue($("#request_form").isDisplayed());
    }
}
