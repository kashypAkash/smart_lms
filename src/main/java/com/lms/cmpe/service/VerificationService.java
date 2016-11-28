package com.lms.cmpe.service;

import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by akash on 11/27/16.
 */

@Service
public class VerificationService {

    public int verficationCode(){
        Random rnd = new Random();
        int verificationCode = 100000 + rnd.nextInt(900000);
        return verificationCode;
    }
}
