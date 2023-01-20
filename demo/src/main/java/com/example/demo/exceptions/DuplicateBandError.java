package com.example.demo.exceptions;

import com.example.demo.model.Band;

public class DuplicateBandError extends RuntimeException {

    public DuplicateBandError(Band band) {
        super("Already a Band with the Name: " + band.getBandName());
    }
}
