package com.inAminute.microservice.currencyconversionservice.Controller;

import com.inAminute.microservice.currencyconversionservice.CurrencyExchangeProxy;
import com.inAminute.microservice.currencyconversionservice.Entity.CurrencyConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

//@Configuration(proxyBeanMethods = false)
//class RestTemplateConfiguration {
//
//    @Bean
//    RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return builder.build();
//    }
//}


@RestController
public class CurrencyConversionController {
    @Autowired
    private CurrencyExchangeProxy proxy;

    //private RestTemplate restTemplate;

    //http://localhost:8100/currency-conversion/fromUSD/to/INR/quantity/quantity
    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculatecurrencyConversion(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
            ){
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
         ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity
                ("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);
         CurrencyConversion currencyConversion = responseEntity.getBody();

        return new CurrencyConversion
                      (
                        currencyConversion.getId(),
                        from,
                        to,
                        quantity,
                        currencyConversion.getConversionMultiple(),
                        quantity.multiply(currencyConversion.getConversionMultiple()),
                        currencyConversion.getEnvironment() +" " + "restTemplate"
                      );
    }
    //http://localhost:8100/currency-conversion-feign/fromUSD/to/INR/quantity/quantity
    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculatecurrencyConversionFeign(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ){
        CurrencyConversion currencyConversion = proxy.retriveExchangeValue(from, to);

        return new CurrencyConversion
                (
                        currencyConversion.getId(),
                        from,
                        to,
                        quantity,
                        currencyConversion.getConversionMultiple(),
                        quantity.multiply(currencyConversion.getConversionMultiple()),
                        currencyConversion.getEnvironment() +" "+"feign"
                );
    }
}
