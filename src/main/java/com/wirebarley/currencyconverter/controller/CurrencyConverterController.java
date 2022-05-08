package com.wirebarley.currencyconverter.controller;

import com.wirebarley.currencyconverter.domain.vo.RecipientCountry;
import com.wirebarley.currencyconverter.dto.CurrencyConverterDto;
import com.wirebarley.currencyconverter.service.CurrencyConverterService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@AllArgsConstructor
@Controller
@Log4j2
public class CurrencyConverterController {

    @Autowired
    private CurrencyConverterService currencyConverterService;

    @RequestMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("recipientCountry", new RecipientCountry());
        return "index";
    }

    @ResponseBody
    @GetMapping(value = "/exchangeRate", params = "toCurrency")
    public CurrencyConverterDto findExchangeRateByCountry(final String toCurrency) throws Exception {
        return currencyConverterService.findExchangeRateByCountry(toCurrency);
    }

    @ResponseBody
    @GetMapping(value = "/receivableAmount", params = {"toCurrency", "sendAmount"})
    public CurrencyConverterDto calculateReceivableAmount(final String toCurrency, final String sendAmount) throws Exception {
        return currencyConverterService.calculateReceivableAmount(toCurrency, sendAmount);
    }
}
