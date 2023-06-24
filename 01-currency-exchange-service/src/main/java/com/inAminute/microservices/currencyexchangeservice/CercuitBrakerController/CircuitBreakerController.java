package com.inAminute.microservices.currencyexchangeservice.CercuitBrakerController;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private Logger logger= LoggerFactory.getLogger(CircuitBreakerController.class);
    @GetMapping("/sample-api")

    //allowing to send repeated request(can configure retryAttempts,waitDuration)
    @Retry(name= "sample-api",fallbackMethod = "hardcodeResponse")

    //allow to customize the number of request and to send number of request after congicutive time
    @CircuitBreaker(name= "sample-api",fallbackMethod = "hardcodeResponse")

    //allow to set a number of request to the api
    @RateLimiter(name="sample-api")

    //defines the number of concurrent call we can make
    @Bulkhead(name="sample-api")

    public String sampleApi(){
        logger.info("sample Api Call recived");
//         ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost8080/some-dummy-url", String.class);
//        return forEntity.getBody();
        return "sample-api";
    }
    public String hardcodeResponse(Exception ex){
        return "fallback-response";
    }
}
