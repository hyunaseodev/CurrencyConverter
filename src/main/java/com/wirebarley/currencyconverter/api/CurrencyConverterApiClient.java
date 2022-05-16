package com.wirebarley.currencyconverter.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wirebarley.currencyconverter.dto.CurrencyConverterDto;
import com.wirebarley.currencyconverter.exception.ExchangeRateNotFoundException;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyConverterApiClient {

    private final String ACCESS_KEY;
    private final String BASE_URL;
    private final String ENDPOINT;

    public CurrencyConverterApiClient(@Value("${currency.access_key}") String ACCESS_KEY, @Value("${currency.base_url}") String BASE_URL,
        @Value("${currency.endpoint}") String ENDPOINT) {
        this.ACCESS_KEY = ACCESS_KEY;
        this.BASE_URL = BASE_URL;
        this.ENDPOINT = ENDPOINT;
    }

    public CurrencyConverterDto findExchangeRateByCountry(final String toCurrency) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = BASE_URL + ENDPOINT + "?access_key=" + ACCESS_KEY + "&currencies=" + toCurrency;

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        if(responseEntity.getBody() == null)
            throw new ExchangeRateNotFoundException("responseEntity is null");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseEntity.getBody());

        boolean success = root.get("success").booleanValue();
        if(!success) {
            String errorCode = root.path("error").get("code").asText();
            String errorInfo = root.path("error").get("info").asText();

            throw new ExchangeRateNotFoundException(errorCode + " / " + errorInfo);
        }

        if(root.isNull() || root.get("source").isNull() || root.path("quotes").isNull())
            throw new ExchangeRateNotFoundException("root-related is null");

        String fromCurrency = root.get("source").textValue();
        String currency = root.path("quotes").fieldNames().next();
        BigDecimal exchangeRate = root.path("quotes").get(currency).decimalValue();

        CurrencyConverterDto currencyConverterDto =CurrencyConverterDto.builder()
            .fromCurrency(fromCurrency)
            .toCurrency(toCurrency)
            .exchangeRate(exchangeRate)
            .build();

        return currencyConverterDto;
    }
}