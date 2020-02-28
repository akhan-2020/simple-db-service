package com.akhan.mongo.demo;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class APIController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private static final Logger logger = LogManager.getLogger(APIController.class);

    @Autowired
    private CustomerRepository repository;

    @PostMapping(value="/customer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Customer customer(@RequestBody Customer customer) {

        logger.debug("Entered greeting method - name : {}", () -> customer.getFirstName());
        // save a couple of customers
        //Customer customer = new Customer(fname, lname, phone);
        repository.save(customer);
        logger.info("Created customer : {} " + customer.getLastName());
        return customer;

    }

    @RequestMapping("/find")
    public Customer find(@RequestParam(value = "phone") String phone) {
        // save a couple of customers
        Customer customer = repository.findByPhone(phone);
        logger.info("found customer ");
        return customer;
    }

}