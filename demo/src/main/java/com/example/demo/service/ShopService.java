package com.example.demo.service;

import com.example.demo.model.Album;
import com.example.demo.model.Shop;
import com.example.demo.repository.AlbumRepository;
import com.example.demo.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {

    private final AlbumRepository albumRepository;

    private final ShopRepository shopRepository;


    public ShopService(AlbumRepository albumRepository, ShopRepository shopRepository) {
        this.albumRepository = albumRepository;
        this.shopRepository = shopRepository;
    }

    public Shop saveNewShop(Shop shop, List<Integer> albumIds) {
        List<Album> albumList = albumRepository.findAllById(albumIds);
        shop.setAlbumList(albumList);
        return shopRepository.save(shop);
    }

    public List<Shop> retrieveShops() {
        return shopRepository.findAll();
    }
}
