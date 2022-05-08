package com.wirebarley.currencyconverter.service;

import com.wirebarley.currencyconverter.api.CurrencyConverterApiClient;
import com.wirebarley.currencyconverter.dto.CurrencyConverterDto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class CurrencyConverterService {
    private final CurrencyConverterApiClient currencyConverterApiClient;

    public CurrencyConverterDto findExchangeRateByCountry(final String toCurrency) throws Exception {
        CurrencyConverterDto currencyConverterDto = currencyConverterApiClient.findExchangeRateByCountry(toCurrency);

        currencyConverterDto = CurrencyConverterDto.builder()
            .fromCurrency(currencyConverterDto.getFromCurrency())
            .toCurrency(currencyConverterDto.getToCurrency())
            .exchangeRate(handleDecimalPoint(currencyConverterDto.getExchangeRate()))
            .build();

        return currencyConverterDto;
    }

    public CurrencyConverterDto calculateReceivableAmount(final String toCurrency, final String sendAmount) throws Exception {
        CurrencyConverterDto currencyConverterDto = currencyConverterApiClient.findExchangeRateByCountry(toCurrency);

        BigDecimal exchangeRate = currencyConverterDto.getExchangeRate();
        BigDecimal receivableAmount = exchangeRate.multiply(new BigDecimal(sendAmount));

        currencyConverterDto = CurrencyConverterDto.builder()
            .fromCurrency(currencyConverterDto.getFromCurrency())
            .toCurrency(currencyConverterDto.getToCurrency())
            .exchangeRate(handleDecimalPoint(currencyConverterDto.getExchangeRate()))
            .receivableAmount(handleDecimalPoint(receivableAmount))
            .build();

        return currencyConverterDto;
    }

    public BigDecimal handleDecimalPoint(final BigDecimal target) {
        return target.setScale(2, RoundingMode.HALF_UP);
    }
}