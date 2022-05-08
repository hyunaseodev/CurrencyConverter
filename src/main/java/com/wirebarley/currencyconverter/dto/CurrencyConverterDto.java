package com.wirebarley.currencyconverter.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CurrencyConverterDto {

    private String fromCurrency;
    private String toCurrency;
    private BigDecimal exchangeRate;

    @Builder
    public CurrencyConverterDto(String fromCurrency, String toCurrency,
        BigDecimal exchangeRate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.exchangeRate = exchangeRate;
    }
}