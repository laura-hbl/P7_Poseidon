package com.nnk.springboot;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Launch Poseidon Application.
 *
 * @author Laura Habdul
 */
@SpringBootApplication
@EnableEncryptableProperties
public class PoseidonApplication {

    /**
     * Starts Poseidon application.
     *
     * @param args no argument
     */
    public static void main(final String[] args) {
        SpringApplication.run(PoseidonApplication.class, args);
    }
}