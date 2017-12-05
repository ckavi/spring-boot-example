package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@EnableAutoConfiguration
@EnableScheduling
@Validated
public class Example {

    @Autowired
    MyConfiguration configuration;

    @Autowired
    ApplicationArguments arguments;

    private static final Logger log = LoggerFactory.getLogger(Example.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @RequestMapping("/")
    String home() {
        return "noluyor lan burda";
    }

    private final AtomicLong counter = new AtomicLong();
    private static final String template = "Hello, %s!";

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") @Size(min = 10,max = 12) String name) {

        System.out.println(arguments.containsOption("debug"));
        System.out.println(configuration.getEnv());

        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Example.class, args);
    }


    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}
