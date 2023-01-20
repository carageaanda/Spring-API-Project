package com.example.demo.exceptions;

import com.example.demo.model.Band;

public class NoMembersNotEqualError extends RuntimeException{

    public NoMembersNotEqualError(Band band) {
        super("The number of members provided (" + band.getNoMembers() + ") is not equal to " +
                "the number of members from the list of artists (" + band.getArtists().size() +
                ")!");
    }
}
