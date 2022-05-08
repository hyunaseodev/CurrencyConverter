package com.wirebarley.currencyconverter.service;

import com.wirebarley.currencyconverter.api.CurrencyConverterApiClient;
import com.wirebarley.currencyconverter.dto.CurrencyConverterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CurrencyConverterService {
    private final CurrencyConverterApiClient currencyConverterApiClient;

    public CurrencyConverterDto findExchangeRateByCountry(final String toCurrency) throws Exception {
        return currencyConverterApiClient.findExchangeRateByCountry(toCurrency);
    }
}
