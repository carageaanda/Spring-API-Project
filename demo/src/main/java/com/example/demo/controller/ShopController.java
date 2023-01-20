package com.example.demo.controller;


import com.example.demo.model.Album;
import com.example.demo.model.Shop;
import com.example.demo.service.AlbumService;
import com.example.demo.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;
    private final AlbumService albumService;


    @Autowired
    public ShopController(ShopService shopService, AlbumService albumService) {
        this.shopService = shopService;
        this.albumService = albumService;
    }


    @PostMapping("/{albumId}")
    public ResponseEntity<?> buyAlbum(@PathVariable int albumId,
                                      @RequestParam String nr,
                                      @RequestParam(required = false) String country) {
        Album album = albumService.buyAlbum(albumId, nr, country);
        return ResponseEntity.ok(album);
    }

    @PostMapping
    public ResponseEntity<Shop> saveNewShop(@RequestBody Shop shop,
                                         @RequestParam List<Integer> albumIds) {
        return ResponseEntity.ok().body(shopService.saveNewShop(shop, albumIds));
    }

    @GetMapping
    public ResponseEntity<List<Shop>> retrieveShop() {
        return ResponseEntity.ok().body(shopService.retrieveShops());
    }


}
