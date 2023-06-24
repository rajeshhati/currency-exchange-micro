package com.inAminute.microservices.currencyexchangeservice.Repository;

import com.inAminute.microservices.currencyexchangeservice.Entity.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {

    CurrencyExchange findByFromAndTo(String from, String to);
}
