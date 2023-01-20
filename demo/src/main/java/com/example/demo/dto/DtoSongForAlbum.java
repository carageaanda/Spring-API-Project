package com.example.demo.dto;

import com.example.demo.model.Languages;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.example.demo.model.Patterns.LENGTH;

public class DtoSongForAlbum {

    @NotBlank(message = "You must enter the song's title!")
    @Size(max = 400)
    private String songTitle;

    @Pattern(regexp = LENGTH, message = "A song must have a length expressed as _h _m _s!")
    private String songLength;

    private Languages languages;

    public DtoSongForAlbum() {
    }

    public DtoSongForAlbum(String songTitle, String songLength, Languages languages) {
        this.songTitle = songTitle;
        this.songLength = songLength;
        this.languages = languages;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongLength() {
        return songLength;
    }

    public void setSongLength(String songLength) {
        this.songLength = songLength;
    }

    public Languages getLanguages() {
        return languages;
    }

    public void setLanguages(Languages languages) {
        this.languages = languages;
    }
}
