package com.iljasstan.Enums;

public enum TestDataEnum {
    VALUE0("Онлайн-кассы"),
    VALUE1("Эквайринг"),
    VALUE2("Кассовая программа"),
    VALUE3("Облачная касса"),
    VALUE4("Готовые решения");

    String desc;

    TestDataEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
