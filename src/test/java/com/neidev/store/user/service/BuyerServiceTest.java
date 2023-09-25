package com.neidev.store.user.service;

import com.neidev.store.handler.exceptions.CredentialAlreadyInUseException;
import com.neidev.store.handler.exceptions.ResourceNotFoundException;
import com.neidev.store.user.entity.Buyer;
import com.neidev.store.user.json.buyer.BuyerResponse;
import com.neidev.store.user.json.buyer.BuyerUpdateForm;
import com.neidev.store.user.repository.BuyerRepository;
import com.neidev.store.user.service.BuyerService;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BuyerServiceTest {

    @Mock
    private BuyerRepository repository;

    @Mock
    private BuyerService mockedService;

    @InjectMocks
    private BuyerService service;

    private Buyer buyer;

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
    void shouldRegisterANewBuyerAndReturnRegisteredBuyerResponse() {
        when(repository.save(any(Buyer.class))).thenReturn(buyer);

        BuyerResponse persistedBuyer = service.registerANewBuyer(buyer);

        verify(repository, times(1)).save(buyer);
        assertEquals(persistedBuyer.getClass(), BuyerResponse.class);

        assertEquals(persistedBuyer.getId(), buyer.getId());
        assertEquals(persistedBuyer.getName(), buyer.getName());
        //assertEquals(persistedBuyer.getLastName(), buyer.getLastName());
        assertEquals(persistedBuyer.getPhoneNumber(), buyer.getPhoneNumber());
        //assertEquals(persistedBuyer.getAddress(), buyer.getAddress());
        //assertEquals(persistedBuyer.getEmail(), buyer.getEmail());
        assertEquals(persistedBuyer.getId(), buyer.getId());
        assertEquals(persistedBuyer.getId(), buyer.getId());
    }

    @Test
    @DisplayName("Test when findAllBuyers fails and throws ResourceNotFoundException")
    void shouldReturnResourceNotFoundExceptionWhenFindAllBuyersFails() {
        when(repository.findAll()).thenReturn(null);

        var exception = assertThrows(ResourceNotFoundException.class, () ->
                                service.findAllBuyers());

        verify(repository, never()).save(buyer);
        assertTrue(exception.getMessage().contains("Cannot find all buyers"));
    }

    @Test
    @DisplayName("Test when findAllBuyers works without any problem")
    void shouldReturnBuyerResponseListContainingAllRegisteredBuyers() {
        Buyer buyer2 = new Buyer(
                "123456789101",
                "Buyer2",
                "Test2",
                "123456789100",
                "Testing Street",
                "buyer@email.com",
                "123",
                "123456789210"
        );
        List<Buyer> repositoryExpectedList = Arrays.asList(buyer, buyer2);
        when(repository.findAll()).thenReturn(repositoryExpectedList);

        List<BuyerResponse> responseServiceList = service.findAllBuyers();
        verify(repository, times(1)).findAll();

        assertNotNull(responseServiceList);
        assertTrue(responseServiceList.size() == 2);
        responseServiceList.forEach(o ->
                assertTrue(o instanceof BuyerResponse)
        );
        var buyerUnderTest = responseServiceList.get(1);
        assertEquals("123456789101", buyerUnderTest.getId());
        assertEquals("Buyer2", buyerUnderTest.getName());
        assertEquals("123456789100", buyerUnderTest.getPhoneNumber());
        assertEquals("123", buyerUnderTest.getPassword());
        assertEquals("123456789210", buyerUnderTest.getCpf());
    }

    @Test
    @DisplayName("Must return ResourceNotFoundException when buyer doesnt exist")
    void shouldReturnResourceNotFoundExceptionWhenFindBuyerByIdFails() {
        var mockedId = UUID.randomUUID().toString();

        when(repository.findById(mockedId)).thenReturn(Optional.empty());

        var exception = assertThrows(ResourceNotFoundException.class, () ->
                service.findBuyerById(mockedId));

        assertTrue(exception.getMessage().contains(mockedId));
        assertTrue(exception.getMessage().contains("Buyer not found"));
    }

    @Test
    @DisplayName("Test when findBuyerById works fine and returns buyer response")
    void shouldFindBuyerAndReturnBuyerResponseById() {
        when(repository.findById(buyer.getId())).thenReturn(Optional.of(buyer));

        BuyerResponse response = service.findBuyerById(buyer.getId());
        verify(repository, times(1)).findById(buyer.getId());

        assertNotNull(response);
        assertTrue(response instanceof BuyerResponse);
        assertEquals("Buyer", response.getName());
    }

    @Test
    @DisplayName("Must return ResourceNotFoundException when findBuyerByEmail fails")
    void shouldReturnResourceNotFoundExceptionWhenFindBuyerByEmailFails() {
        when(repository.findByEmail(buyer.getEmail())).thenReturn(Optional.empty());

        var exception = assertThrows(ResourceNotFoundException.class, () ->
                service.findBuyerByEmail(buyer.getEmail()));

        assertTrue(exception.getMessage().contains(buyer.getEmail()));
        assertTrue(exception.getMessage().contains("Buyer not found"));
    }

    @Test
    @DisplayName("Test when findBuyerByEmail works fine and return buyer response")
    void shouldFindBuyerAndReturnResponseBuyerByEmail() {
        when(repository.findByEmail(buyer.getEmail())).thenReturn(Optional.of(buyer));

        var responseUnderTest = service.findBuyerByEmail(buyer.getEmail());
        verify(repository, times(1)).findByEmail(buyer.getEmail());

        assertNotNull(responseUnderTest);
        assertTrue(responseUnderTest instanceof BuyerResponse);
        assertEquals("Buyer", responseUnderTest.getName());
    }

    @Test
    @DisplayName("Must return ResourceNotFoundException when pass")
    void shouldReturnResourceNotFoundExceptionWhenDeleteBuyerByIdFails() {
        when(repository.findById(buyer.getId())).thenReturn(Optional.empty());

        var exception = assertThrows(ResourceNotFoundException.class, () ->
                service.deleteBuyerById(buyer.getId()));

        verify(repository, never()).deleteById(buyer.getId());
        assertTrue(exception.getMessage().contains(buyer.getId()));
        assertTrue(exception.getMessage().contains("Buyer doesn't exist!"));
    }

    @Test
    @DisplayName("Test when deleteBuyerById works fine and returns nothing")
    void shouldDeleteBuyerByIdAndReturnNothing() {
        doNothing().when(repository).deleteById(any());
        when(repository.findById(buyer.getId())).thenReturn(Optional.of(buyer));

        service.deleteBuyerById(buyer.getId());
        verify(repository, times(1)).deleteById(buyer.getId());
    }

    @Test
    @DisplayName("Must return ResourceNotFoundException when passes")
    void shouldThrowResourceNotFoundExceptionWhenPasses() {
        when(repository.findById(buyer.getId())).thenReturn(Optional.empty());

        BuyerUpdateForm form = new BuyerUpdateForm();

        var exception = assertThrows(ResourceNotFoundException.class, () ->
                service.updateRegisteredBuyer(form, buyer.getId()));

        verify(repository, never()).save(buyer);
        assertTrue(exception.getMessage().contains(buyer.getId()));
        assertTrue(exception.getMessage().contains("Buyer not found"));
    }

    @Test
    @DisplayName("Test when updateRegisteredBuyer works fine and update buyer")
    void shouldFindBuyerByIdUpdateBuyerAndReturnBuyerResponse() {
        BuyerUpdateForm form = new BuyerUpdateForm(
          "updatedEmail@email.com",
          "123",
          "Updated Address",
          "123456789100"
        );
        when(repository.findById(buyer.getId())).thenReturn(Optional.of(buyer));
        when(repository.save(buyer)).thenReturn(buyer);

        BuyerResponse updatedBuyer = service.updateRegisteredBuyer(form, buyer.getId());
        verify(repository, times(1)).save(buyer);

        assertEquals("updatedEmail@email.com", updatedBuyer.getEmail());
        assertEquals("123", updatedBuyer.getPassword());
        assertEquals("Updated Address", updatedBuyer.getAddress());
        assertEquals("123456789100", updatedBuyer.getPhoneNumber());
    }


}
