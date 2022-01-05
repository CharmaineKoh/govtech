package com.charmaine.govtech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import com.charmaine.govtech.data.UserRepository;
import com.charmaine.govtech.domains.User;

@Component
public class MyRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyRunner.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        logger.info("Pre-loaded");

        User u1 = new User("John", 2500.05);
        userRepository.save(u1);

        User u2 = new User("Mary Posa", 4000.00);
        userRepository.save(u2);
    }
}