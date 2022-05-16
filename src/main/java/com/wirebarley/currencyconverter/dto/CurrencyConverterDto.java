package com.wirebarley.currencyconverter.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CurrencyConverterDto {

    private final String fromCurrency;
    private final String toCurrency;
    private final BigDecimal exchangeRate;
    private final BigDecimal receivableAmount;
}