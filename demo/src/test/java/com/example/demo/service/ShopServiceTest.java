package com.example.demo.service;


import com.example.demo.model.Album;
import com.example.demo.model.Shop;
import com.example.demo.repository.AlbumRepository;
import com.example.demo.repository.ShopRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ShopServiceTest {

    @InjectMocks
    private ShopService shopService;

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private ShopRepository shopRepository;


    @Test
    public void testSaveNewShop_success() {
        // given
        Shop shop = new Shop( "location1");
        Album album1 = new Album("albumNameTest","2000", 3);
        Album album2 = new Album("albumNameTest2", "2000", 3);
        List<Integer> albumIds = Arrays.asList(1, 2);
        when(albumRepository.findAllById(albumIds)).thenReturn(Arrays.asList(album1, album2));
        when(shopRepository.save(shop)).thenReturn(shop);

        // when
        Shop savedShop = shopService.saveNewShop(shop, albumIds);

        // then
        verify(albumRepository).findAllById(albumIds);
        verify(shopRepository).save(shop);
        assertSame(shop, savedShop);
    }

    @Test
    public void testRetrieveShops_success() {
        // given
        Shop shop1 = new Shop( "location1");
        Shop shop2 = new Shop( "location2");
        List<Shop> expectedShops = Arrays.asList(shop1, shop2);
        when(shopRepository.findAll()).thenReturn(expectedShops);

        // when
        List<Shop> actualShops = shopService.retrieveShops();

        // then
        verify(shopRepository).findAll();
        assertSame(expectedShops, actualShops);
    }
}




