package com.common.event;

import com.common.event.impl.TestEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventApplication.class, args);
        EventBus.getInstance().fire(new TestEvent(22222));
    }

}
