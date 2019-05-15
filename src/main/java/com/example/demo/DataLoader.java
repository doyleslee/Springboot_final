package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    MessageRepository repository;

    @Override
    public void run(String... strings) throws Exception{
        Message msg = new Message("First message of many to be sent to here", "2018-06-30", "Carl");
        repository.save(msg);

        msg = new Message("Thank god for data loaders so I don't need to constantly resubmit", "2018-04-22","Bob");
        repository.save(msg);

        msg = new Message("typical sorority girl pic.png", "2019-01-10", "Jack");
        repository.save(msg);
    }
}
