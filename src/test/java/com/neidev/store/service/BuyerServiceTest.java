package com.neidev.store.service;

import com.neidev.store.user.entity.Buyer;
import com.neidev.store.user.repository.BuyerRepository;
import com.neidev.store.user.service.BuyerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class BuyerServiceTest {

    @Mock
    BuyerRepository repository;

    @InjectMocks
    BuyerService service;

    Buyer buyer;

    @BeforeEach
    public void setUp() {
        buyer = new Buyer(
                "123",
                "Nathan",
                "Barros",
                "11941956585",
                "Rua Legal",
                "nathan@gmail.com",
                "123",
                "12345678910"
        );

        service = Mockito.mock(BuyerService.class);
        repository = Mockito.mock(BuyerRepository.class);
    }

    @Test
    void shouldReturnRegisteredBuyer() {
        Buyer persistedBuyer = new Buyer("123",
                "Nathan",
                "Barros",
                "11941956585",
                "Rua Legal",
                "nathan@gmail.com",
                "123",
                "12345678910");

        when(repository.save(buyer))
                .thenReturn(persistedBuyer);

        assertEquals(persistedBuyer.getId(), buyer.getId());

    }


}
