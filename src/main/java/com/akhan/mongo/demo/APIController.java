package com.akhan.mongo.demo;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.jms.*;

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

    @RequestMapping("/customer/phone/{phone}")
    public Customer find(@PathVariable(value = "phone") String phone) throws DataAccessException {

        /*
        int random = insertRandomDelay();
        if(random >=9){
            throw new DataAccessException("Data access error occured");
        }
        */

       // int random = insertRandomDelay();

        Customer customer = repository.findByPhone(phone);
        if(customer==null) {
            throw new DataAccessException("No Customers found");
        }
        logger.info("found customer ");
        return customer;
    }

    /*
      insert random delay between 1 - 3 seconds if invocation falls in delay range
     */
    private int insertRandomDelay() {
        int rand = (int) (Math.random() * 10) + 1;
        //introduce random delay
        if (rand <= 2) {
            try {
                Thread.sleep(rand * 2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return rand;
    }


    private void writeMessageToQ(String message){

        // Create a connection factory.
        final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("ssl://b-1e1acf2d-2922-4ef2-b11f-63218a0911fa-1.mq.us-east-2.amazonaws.com:61617");

        // Pass the username and password.
        connectionFactory.setUserName("adminmgi");
        connectionFactory.setPassword("P@ssword2020");

        // Create a pooled connection factory.
        final PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(connectionFactory);
        pooledConnectionFactory.setMaxConnections(10);

        // Establish a connection for the producer.
        final Connection producerConnection;
        try {
            producerConnection = pooledConnectionFactory.createConnection();
            producerConnection.start();


            // Create a session.
            final Session producerSession = producerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create a queue named "MyQueue".
            final Destination producerDestination = producerSession.createQueue("MyQueue");

            // Create a producer from the session to the queue.
            final MessageProducer producer = producerSession.createProducer(producerDestination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);


            // Create a message.

            TextMessage producerMessage = producerSession.createTextMessage(message);

            // Send the message.
            producer.send(producerMessage);
            System.out.println("Message sent.");

        } catch (JMSException e) {
            e.printStackTrace();
        }




    }

}