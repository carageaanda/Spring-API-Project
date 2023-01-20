package com.example.demo.mapper;

import com.example.demo.dto.DtoSongForAlbum;
import com.example.demo.model.Song;
import org.springframework.stereotype.Component;

@Component
public class SongMapper {

    public Song createSongDtoToSongFromAlbum(DtoSongForAlbum dtoSongForAlbum) {
        return new Song(dtoSongForAlbum.getSongTitle(), dtoSongForAlbum.getSongLength(), dtoSongForAlbum.getLanguages());
    }

}
