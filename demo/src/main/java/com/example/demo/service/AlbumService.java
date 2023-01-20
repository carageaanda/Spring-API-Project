package com.example.demo.service;


import com.example.demo.exceptions.*;
import com.example.demo.model.Album;
import com.example.demo.model.AlbumDetails;
import com.example.demo.model.MoneyConverter;
import com.example.demo.model.Song;
import com.example.demo.repository.AlbumDetailsRepository;
import com.example.demo.repository.AlbumRepository;
import com.example.demo.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final AlbumDetailsRepository albumDetailsRepository;
    private final SongRepository  songRepository;


    @Autowired
    public AlbumService(AlbumRepository albumRepository, AlbumDetailsRepository albumDetailsRepository ,SongRepository songRepository) {
        this.albumRepository = albumRepository;
        this.albumDetailsRepository = albumDetailsRepository;
        this.songRepository = songRepository;
    }

    //Save a new Album. The Album must belong to an existing Band. Also add the detials!
    public Album saveNewAlbum(Album album){
        Optional<Album> albumIsFound = albumRepository.findByAlbumNameAndBand(album.getAlbumName(), album.getBand());
        if (albumIsFound.isPresent()) {
            throw new DuplicateAlbumError(album);
        }
        if(!album.getSongs().isEmpty()) {
            if(album.getNoTrakcs() != album.getSongs().size()) {
                throw new NoTracksNotEqualError(album);
            }
        }
        AlbumDetails albumDetails = album.getAlbumDetails();
        albumDetails.setAlbum(album);
        albumDetailsRepository.save(albumDetails);
        albumRepository.save(album);

        //Set songs in the Album
        for (Song song: album.getSongs()) {
            Song s = songRepository.getById(song.getSongId());
            s.setAlbum(album);
            songRepository.save(s);
        }
        return album;
    }

    //Update Album's title / Update Album's year
    public Album updateAlbum(Album album) {
        Album foundAlbum = albumRepository.findById(album.getAlbumId()).orElseThrow(
                () -> new AlbumNotFoundError()
        );
        if(!album.getAlbumName().equals(foundAlbum.getAlbumName())) {
            Optional<Album> albumExists = albumRepository.findByAlbumNameAndBand(album.getAlbumName(), foundAlbum.getBand());
            if(albumExists.isPresent()) {
                throw new DuplicateAlbumError(album.getAlbumName(), foundAlbum);
            }
        }
        Album a = new Album(album.getAlbumId(), album.getAlbumName(), album.getAlbumYear(),
                foundAlbum.getNoTrakcs(), foundAlbum.getBand(), foundAlbum.getAlbumDetails(), foundAlbum.getSongs());

        return albumRepository.save(a);
    }

    //Add Song in the Album
    public Album addNewSongToAlbum(int id, Song song) {
        Optional<Album> foundAlbum = albumRepository.findById(id);
        if(foundAlbum.isEmpty()){
            throw  new AlbumNotFoundError();
        }
        Album album = foundAlbum.get();

        //Set album to song
        song.setAlbum(album);
        songRepository.save(song);
        //Modify number of tracks with 1 and add song to the list
        album.setNoTracks(album.getNoTrakcs() + 1);
        List<Song> songs = album.getSongs();
        songs.add(song);
        album.setSongs(songs);
        return albumRepository.save(album);
    }

    //Adding a song that can be from another album or not, in a new album
    public Album addExistingSongToAlbum(int id, int songId) {

        //check if album exists

        Optional<Album> foundAlbum = albumRepository.findById(id);
        if(foundAlbum.isEmpty()) {
            throw new AlbumNotFoundError();
        }

        //check if song exists

        Optional<Song> foundSong = songRepository.findById(songId);
        if(foundSong.isEmpty()) {
            throw new SongNotFoundError();
        }

        //add it in Album

        Song song = foundSong.get();
        Album album = foundAlbum.get();
        album.setNoTracks(album.getNoTrakcs() + 1);
        List<Song> songList = album.getSongs();
        songList.add(song);
        album.setSongs(songList);
        albumRepository.save(album);
        song.setAlbum(album);
        songRepository.save(song);
        return album;
    }

    //Delete Song from Album
    public Album deleteSongFromAlbum (int id, int songId) {

        //If Album is not Found

        Optional<Album> foundAlbum = albumRepository.findById(id);
        if(foundAlbum.isEmpty()) {
            throw new AlbumNotFoundError();
        }

        //If Song is not Found

        Optional<Song> foundSong = songRepository.findById(songId);
        if(foundSong.isEmpty()) {
            throw new SongNotFoundError();
        }

        //If Song is not from the Album provided

        if(foundSong.get().getAlbum() == null || foundSong.get().getAlbum().getAlbumId() != id) {
            throw new InvalidRequest(id);
        }
        Song song = foundSong.get();

        //Song is not in the album any-longer
        song.setAlbum(null);
        songRepository.save(song);

        //Delete Song from Album
        Album album = foundAlbum.get();
        album.setNoTracks(album.getNoTrakcs() - 1);
        List<Song> songs = album.getSongs();
        songs.remove(song);
        album.setSongs(songs);
        return albumRepository.save(album);
    }

    //Get all Albums
    public Object getAllAlbums(String albumYear, String price) {
        if(albumYear != null) return albumRepository.findByAlbumYear(albumYear);
        if(price != null) return albumRepository.findByAlbumDetails_Price(Double.valueOf(price));
        return albumRepository.findAll();
    }

    @Transactional
    public Album buyAlbum(int albumId, String nr, String country) {

        //check if album exists
        Optional<Album> foundAlbum = albumRepository.findById(albumId);
        if(foundAlbum.isEmpty()) {
            throw new AlbumNotFoundError();
        }

        //check if album is still in stock and if the number of albums wanted exists

        Album album = foundAlbum.get();
        System.out.println("Anda");
        System.out.println(album.toString());
        System.out.println("-------------------");
        System.out.println(album.getAlbumDetails());
        System.out.println(country);
        if(album.getAlbumDetails().getQuantity() == 0 || album.getAlbumDetails().getQuantity() < Integer.valueOf(nr)) {
            throw new EmptyStockError();
        }

        //Establish an amount that the buyer owns, the money depending on the buyer's country of origin
        double amount = 30;
        MoneyConverter moneyConverter = new MoneyConverter();
        double price = album.getAlbumDetails().getPrice() * Integer.valueOf(nr);
        if(country != null) {
            if (country.equalsIgnoreCase("FR")) {
                price = moneyConverter.convertToEuros(price);
            } else if (country.equalsIgnoreCase("RO")) {
                price = moneyConverter.convertToRon(price);
            } else {
                throw new NoMoneyError();
            }
        }


        if(amount < price) {
            throw new NoMoneyError();
        }

        amount = amount - price;
        System.out.println(amount);

        double quantity = album.getAlbumDetails().getQuantity() - 1;
        AlbumDetails albumdetails = album.getAlbumDetails();
        albumdetails.setQuantity(quantity);
        albumDetailsRepository.save(albumdetails);
        album.setAlbumDetails(albumdetails);
        return albumRepository.save(album);

    }


}


