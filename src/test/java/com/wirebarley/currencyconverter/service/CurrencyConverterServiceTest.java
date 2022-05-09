package com.wirebarley.currencyconverter.service;

import java.math.BigDecimal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CurrencyConverterServiceTest {

    @Autowired
    CurrencyConverterService currencyConverterService;

    @Test
    @DisplayName("소수점 반올림")
    void handleDecimalPoint(){
        BigDecimal target1 = new BigDecimal("34.1575357");
        BigDecimal target2 = new BigDecimal("34.1411");

        Assertions.assertThat(currencyConverterService.handleDecimalPoint(target1)).isEqualTo(new BigDecimal("34.16"));
        Assertions.assertThat(currencyConverterService.handleDecimalPoint(target2)).isEqualTo(new BigDecimal("34.14"));
    }
}