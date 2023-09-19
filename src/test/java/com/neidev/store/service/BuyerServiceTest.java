package com.neidev.store.service;

import com.neidev.store.handler.exceptions.CredentialAlreadyInUseException;
import com.neidev.store.user.entity.Buyer;
import com.neidev.store.user.repository.BuyerRepository;
import com.neidev.store.user.service.BuyerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BuyerServiceTest {

    @Mock
    private BuyerRepository repository;

    @InjectMocks
    private BuyerService service;

    Buyer buyer;

    @BeforeEach
    public void setUp() {
        buyer = new Buyer(
                "12345678910",
                "Buyer",
                "Test",
                "123456789100",
                "Testing Street",
                "buyer@email.com",
                "123",
                "12345678910"
        );
    }

    @Test
    @DisplayName("Test when registerANewBuyer fails with email already registered")
    void shouldReturnCredentialAlreadyInUseExceptionWithEmailAlreadyInUse() {
        when(repository.findByEmail(buyer.getEmail())).thenReturn(Optional.of(buyer));

        var exception = assertThrows(CredentialAlreadyInUseException.class, () ->
                service.registerANewBuyer(buyer));

        verify(repository, never()).save(buyer);
        assertTrue(exception.getMessage().contains(buyer.getEmail()));
        assertTrue(exception.getMessage().contains("Email already registered!"));
        assertTrue(exception.getMessage().contains("buyer@email.com"));
    }

    @Test
    @DisplayName("Test when registerANewBuyer fails with CPF already registered")
    void shouldReturnCredentialAlreadyInUseExceptionWithCpfAlreadyInUse() {
        when(repository.findByCpf(buyer.getCpf())).thenReturn(Optional.of(buyer));

        var exception = assertThrows(CredentialAlreadyInUseException.class, () ->
                service.registerANewBuyer(buyer));

        verify(repository, never()).save(buyer);
        assertTrue(exception.getMessage().contains(buyer.getCpf()));
        assertTrue(exception.getMessage().contains("CPF already registered!"));
        assertTrue(exception.getMessage().contains("12345678910"));
    }

    @Test
    @DisplayName("Test when registerANewBuyer works fine")
    void shouldRegisterANewBuyerAndReturnRegisteredBuyer() {
        when(repository.save(any(Buyer.class))).thenReturn(buyer);

        Buyer persistedBuyer = service.registerANewBuyer(buyer);

        verify(repository, times(1)).save(buyer);
        assertEquals(persistedBuyer.getId(), buyer.getId());
        assertEquals(persistedBuyer.getName(), buyer.getName());
        assertEquals(persistedBuyer.getLastName(), buyer.getLastName());
        assertEquals(persistedBuyer.getPhoneNumber(), buyer.getPhoneNumber());
        assertEquals(persistedBuyer.getAddress(), buyer.getAddress());
        assertEquals(persistedBuyer.getEmail(), buyer.getEmail());
        assertEquals(persistedBuyer.getId(), buyer.getId());
        assertEquals(persistedBuyer.getId(), buyer.getId());
    }

}
