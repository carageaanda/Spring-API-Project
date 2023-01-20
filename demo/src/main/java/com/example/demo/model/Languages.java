package com.example.demo.model;

public enum Languages {
    ENGLISH, FRENCH, SPANISH, ROMANIAN;

    static public boolean isMember(String language) {
        Languages[] languages = Languages.values();
        for(Languages l: languages) {
            if(l.equals(language))
                return true;
        }
        return false;
    }
}

