package com.wirebarley.currencyconverter.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.wirebarley.currencyconverter.dto.CurrencyConverterDto;
import com.wirebarley.currencyconverter.service.CurrencyConverterService;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CurrencyConverterController.class)
public class CurrencyConverterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyConverterService currencyConverterService;

    @Test
    @DisplayName("응답 상태와 반환타입 결과 테스트")
    void findExchangeRateByCountry() throws Exception {
        CurrencyConverterDto content =CurrencyConverterDto.builder()
            .fromCurrency("USD")
            .toCurrency("PHP")
            .exchangeRate(new BigDecimal("52.549506"))
            .build();

        mockMvc.perform(get("/currencyconverter").param("selectdCountry", "PHP")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());

        when(currencyConverterService.findExchangeRateByCountry(any())).thenReturn(content);
    }
}