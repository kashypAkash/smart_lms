package com.lms.cmpe.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * Created by raghu on 12/6/16.
 */


public class LmsException extends Exception {
    public LmsException(String errorString) {
        super(errorString);
    }
}
