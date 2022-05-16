package com.wirebarley.currencyconverter.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RecipientCountryEnum {

    KRW("한국(KRW)"),
    JPY("일본(JPY)"),
    PHP("필리핀(PHP)");

    private String displayValue;
}
