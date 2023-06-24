package com.inAminute.microservices.currencyexchangeservice.Controller;


import com.inAminute.microservices.currencyexchangeservice.Entity.CurrencyExchange;
import com.inAminute.microservices.currencyexchangeservice.Repository.CurrencyExchangeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
public class CurrencyExchangeController {

    private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;
    @Autowired
    private Environment environment;

    //http://localhost:8000/currency-exchange/from/USD/to/INR
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retriveExchangeValue
                (
                        @PathVariable String from,@PathVariable String to
                ){
        logger.info("retriveExchangeValue called with {} to {}", from, to);
//        CurrencyExchange currencyExchange = new CurrencyExchange(1000l, from, to, BigDecimal.valueOf(50));
         CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
         if(currencyExchange==null){
             throw new  RuntimeException("unable to find data for:"+from+ "to" +to);
         }
        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
        return currencyExchange;


    }
}
