package com.example.demo;

import com.example.demo.mapper.AlbumMapper;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    private final BandRepository bandRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final AlbumDetailsRepository albumDetailsRepository;
    private final SongRepository songRepository;
    private final GenreRepository genreRepository;

    private final ShopRepository shopRepository;


    public DemoApplication(BandRepository bandRepository, ArtistRepository artistRepository, AlbumRepository albumRepository, AlbumDetailsRepository albumDetailsRepository, SongRepository songRepository, GenreRepository genreRepository, ShopRepository shopRepository) {
        this.bandRepository = bandRepository;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
        this.albumDetailsRepository = albumDetailsRepository;
        this.songRepository = songRepository;
        this.genreRepository = genreRepository;
        this.shopRepository = shopRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Genre genre1 = new Genre("Pop");
        Genre genre2 = new Genre("Dance-Pop");
        Genre genre3 = new Genre("Electro-Pop");
        Genre genre4 = new Genre("Hyper-Pop");

        genreRepository.save(genre1);
        genreRepository.save(genre2);
        genreRepository.save(genre3);
        genreRepository.save(genre4);

        Song song1 = new Song("Energy","3m 20s", Languages.ENGLISH);
        Song song2 = new Song("Love Story", "3m 40s", Languages.SPANISH);
        Song song3 = new Song("Make u Mine", "4M 20S", Languages.ENGLISH);
        Song song4 = new Song("Do for You", "4m 13S", Languages.ENGLISH);
        Song song5 = new Song("Pacific life", "3M 20S", Languages.ENGLISH);
        Song song6 = new Song("Sunwave", "2M 20S", Languages.ENGLISH);


        AlbumDetails albumDetails1 = new AlbumDetails(27.50,5);

        Album album1 = new Album("Gummy","2017",4);


        Artist artist1 = new Artist("Kyle", "Andrew", "K8", "20-Oct-1997");
        Artist artist2 = new Artist("Willy", "Damian", "Manny", "04-Jan-1995");
        Artist artist3 = new Artist("Britney", "Losh", "stagename", "23-Dec-1998");
        Artist artist4 = new Artist("Kyle", "Andrew", "stagename", "25-Jan-1995");



        Band band1 = new Band("AOE",4,"2015","0");
        bandRepository.save(band1);

        Shop shop = new Shop("Los Angeles");
        shopRepository.save(shop);

        shop.setAlbumList(List.of(album1));
        artist1.setBand(band1);
        artist2.setBand(band1);
        artist3.setBand(band1);
        artist4.setBand(band1);
        artistRepository.save(artist1);
        artistRepository.save(artist2);
        artistRepository.save(artist3);
        artistRepository.save(artist4);
        albumDetailsRepository.save(albumDetails1);
        album1.setBand(band1);
        album1.setAlbumDetails(albumDetails1);
        albumRepository.save(album1);
        song1.setAlbum(album1);
        song2.setAlbum(album1);
        song3.setAlbum(album1);
        song4.setAlbum(album1);
        song5.setAlbum(album1);
        song6.setAlbum(album1);
        song1.setGenres(Arrays.asList(genre1,genre2));
        song2.setGenres(Arrays.asList(genre1, genre3));
        song3.setGenres(Arrays.asList(genre1, genre4));
        song4.setGenres(Arrays.asList(genre1, genre2, genre3));
        song5.setGenres(Arrays.asList(genre1));
        song6.setGenres(Arrays.asList(genre1, genre2, genre4));
        songRepository.save(song1);
        songRepository.save(song2);
        songRepository.save(song3);
        songRepository.save(song4);
        songRepository.save(song5);
        songRepository.save(song6);






    }


}
