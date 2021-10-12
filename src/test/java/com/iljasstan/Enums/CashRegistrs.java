package com.iljasstan.Enums;

public enum CashRegistrs {
    CASHREGISTR0("Стационарные кассы"),
    CASHREGISTR1("Мобильные кассы"),
    CASHREGISTR2("Интернет-касса"),
    CASHREGISTR3("Облачная касса");

    String desc;

    CashRegistrs(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
