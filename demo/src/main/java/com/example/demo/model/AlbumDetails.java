package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name="album_details")
public class AlbumDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="album_details_id")
    private int idAlbumDetails;

    @Min(value = 0, message = "The price must be a positive number!")
    private double price;

    @Min(value = 0, message = "The quantity must be a positive number!")
    private double quantity;

    @OneToOne(mappedBy = "albumDetails", cascade = CascadeType.ALL)
    @JsonIgnore
    private Album album;

    public AlbumDetails() {
    }

    public AlbumDetails(int idAlbumDetails, double price, double quantity) {
        this.idAlbumDetails = idAlbumDetails;
        this.price = price;
        this.quantity = quantity;
    }

    public AlbumDetails(double price, double quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public int getIdAlbumDetails() {
        return idAlbumDetails;
    }

    public void setIdAlbumDetails(int idAlbumDetails) {
        this.idAlbumDetails = idAlbumDetails;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
