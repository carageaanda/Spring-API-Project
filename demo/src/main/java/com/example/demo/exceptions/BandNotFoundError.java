package com.example.demo.exceptions;

import com.example.demo.model.Band;

public class BandNotFoundError extends RuntimeException{

    public BandNotFoundError(Band band) {
        super("The Band with the ID " + band.getBandId() + " was not found in the database!");
    }
    public BandNotFoundError() {
        super("Not found in the database!");
    }
}
