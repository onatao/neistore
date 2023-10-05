package com.neidev.store.user.service;

import com.neidev.store.domain.user.service.SellerService;
import com.neidev.store.handler.exceptions.CredentialAlreadyInUseException;
import com.neidev.store.handler.exceptions.ResourceNotFoundException;
import com.neidev.store.domain.user.entity.Seller;
import com.neidev.store.domain.user.json.seller.SellerResponse;
import com.neidev.store.domain.user.json.seller.SellerUpdateForm;
import com.neidev.store.domain.user.repository.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SellerServiceTest {

    @Mock
    private SellerRepository repository;

    @InjectMocks
    private SellerService service;

    private Seller seller;

    @BeforeEach
    void setUp() {
        seller = new Seller(
                "12345678910",
                "Seller",
                "Test",
                "123456789100",
                "Testing Street",
                "seller@email.com",
                "123",
                "12345678910000"
        );
    }


    @Test
    @DisplayName("Throw an CredentialsAlreadyInUse exception when register using invalid Email")
    void mustThrowCredentialAlreadyInUseExceptionWhenRegisterANewSellerFailsWithEmail() {
        when(repository.findByEmail(seller.getEmail())).thenReturn(Optional.of(seller));

        var exception = assertThrows(CredentialAlreadyInUseException.class, () ->
                service.registerANewSeller(seller));

        verify(repository, never()).save(seller);
        assertTrue(exception.getMessage().contains(seller.getEmail()));
        assertTrue(exception.getMessage().contains("Email already registered!"));
    }

    @Test
    @DisplayName("Throw an CredentialAlreadyInUse exception when register using invalid CNPJ")
    void mustThrowCredentialAlreadyInUseExceptionWhenRegisterANewSellerFailsWithCnpj() {
        when(repository.findByCnpj(seller.getCnpj())).thenReturn(Optional.of(seller));

        var exception = assertThrows(CredentialAlreadyInUseException.class, () ->
                service.registerANewSeller(seller));

        verify(repository, never()).save(seller);
        assertTrue(exception.getMessage().contains(seller.getCnpj()));
        assertTrue(exception.getMessage().contains("CNPJ already registered!"));
    }

    @Test
    @DisplayName("Must register a seller and return registered seller with success")
    void shouldRegisterANewSellerAndReturnRegisteredSellerWithSuccess() {
        when(repository.save(seller)).thenReturn(seller);

        var persistedSeller = service.registerANewSeller(seller);
        verify(repository, times(1)).save(seller);
        assertEquals(persistedSeller.getClass(), SellerResponse.class);

        assertNotNull(persistedSeller);
        assertEquals(persistedSeller.getId(), seller.getId());
        assertEquals(persistedSeller.getName(), seller.getName());
        assertEquals(persistedSeller.getLastName(), seller.getLastName());
        assertEquals(persistedSeller.getPhoneNumber(), seller.getPhoneNumber());
        assertEquals(persistedSeller.getAddress(), seller.getAddress());
        assertEquals(persistedSeller.getEmail(), seller.getEmail());
        assertEquals(persistedSeller.getPassword(), seller.getPassword());
        assertEquals(persistedSeller.getCnpj(), seller.getCnpj());
    }

    @Test
    @DisplayName("Throw an ResourceNotFoundException when findAll fails")
    void mustThrowResourceNotFoundExceptionWhenFindAllSellersFails() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        var exception = assertThrows(ResourceNotFoundException.class, () ->
                service.findAllSellers());

        verify(repository, times(1)).findAll();
        assertTrue(exception.getMessage().contains("Cannot get all registered sellers"));
    }

    @Test
    @DisplayName("Must return a list containing all registered sellers")
    void shouldFindAllSellersAndReturnSellerResponseList() {
        when(repository.findAll()).thenReturn(List.of(seller));

        List<SellerResponse> response = service.findAllSellers();
        var sellerUnderTest = response.get(0);

        verify(repository, times(1)).findAll();
        response.forEach(s ->
                assertTrue(s instanceof SellerResponse)
        );

        assertEquals("12345678910", sellerUnderTest.getId());
        assertEquals( "Seller", sellerUnderTest.getName());
        assertEquals("Test", sellerUnderTest.getLastName());
        assertEquals( "123456789100", sellerUnderTest.getPhoneNumber());
        assertEquals( "Testing Street", sellerUnderTest.getAddress());
        assertEquals("seller@email.com", sellerUnderTest.getEmail());
        assertEquals("123", sellerUnderTest.getPassword());
        assertEquals(  "12345678910000", sellerUnderTest.getCnpj());
    }

    @Test
    @DisplayName("Throw an ResourceNotFoundException when findById fails")
    void mustThrowResourceNotFoundExceptionWhenFindSellerByIdFails() {
        when(repository.findById(seller.getId())).thenReturn(Optional.empty());

        var exception = assertThrows(ResourceNotFoundException.class, () ->
                service.findSellerById(seller.getId()));

        verify(repository, times(1)).findById(seller.getId());
        assertTrue(exception.getMessage().contains(seller.getId()));
        assertTrue(exception.getMessage().contains("Seller not found."));
    }

    @Test
    @DisplayName("Must find a seller by id and return it")
    void shouldFindASellerByIdAndReturnSellerResponse() {
        when(repository.findById(seller.getId())).thenReturn(Optional.of(seller));

        var sellerUnderTest = service.findSellerById(seller.getId());
        verify(repository, times(1)).findById(seller.getId());

        assertEquals("12345678910", sellerUnderTest.getId());
        assertEquals( "Seller", sellerUnderTest.getName());
        assertEquals("Test", sellerUnderTest.getLastName());
        assertEquals( "123456789100", sellerUnderTest.getPhoneNumber());
        assertEquals( "Testing Street", sellerUnderTest.getAddress());
        assertEquals("seller@email.com", sellerUnderTest.getEmail());
        assertEquals("123", sellerUnderTest.getPassword());
        assertEquals(  "12345678910000", sellerUnderTest.getCnpj());
    }

    @Test
    @DisplayName("Throw an ResourceNotFoundException when findByEmail fails")
    void mustThrowResourceNotFoundExceptionWhenFindSellerByEmailFails() {
        when(repository.findByEmail(seller.getEmail())).thenReturn(Optional.empty());

        var exception = assertThrows(ResourceNotFoundException.class, () ->
                        service.findSellerByEmail(seller.getEmail())
                    );

        verify(repository, times(1)).findByEmail(seller.getEmail());
        assertTrue(exception.getMessage().contains(seller.getEmail()));
        assertTrue(exception.getMessage().contains("Cannot find seller by email: "));
    }

    @Test
    @DisplayName("Must find a seller by email and return it")
    void shouldFindASellerByEmailAndReturnSellerResponse() {
        when(repository.findByEmail(seller.getEmail())).thenReturn(Optional.of(seller));

        var sellerUnderTest = service.findSellerByEmail(seller.getEmail());
        verify(repository, times(1)).findByEmail(seller.getEmail());

        assertEquals("12345678910", sellerUnderTest.getId());
        assertEquals( "Seller", sellerUnderTest.getName());
        assertEquals("Test", sellerUnderTest.getLastName());
        assertEquals( "123456789100", sellerUnderTest.getPhoneNumber());
        assertEquals( "Testing Street", sellerUnderTest.getAddress());
        assertEquals("seller@email.com", sellerUnderTest.getEmail());
        assertEquals("123", sellerUnderTest.getPassword());
        assertEquals(  "12345678910000", sellerUnderTest.getCnpj());
    }

    @Test
    @DisplayName("Throw an ResourceNotFoundException when findByCnpj fails")
    void mustThrowResourceNotFoundExceptionWhenFindSellerByCnpjFails() {
        when(repository.findByCnpj(seller.getCnpj())).thenReturn(Optional.empty());

        var exception = assertThrows(ResourceNotFoundException.class, () ->
                service.findSellerByCnpj(seller.getCnpj())
        );

        verify(repository, times(1)).findByCnpj(seller.getCnpj());
        assertTrue(exception.getMessage().contains(seller.getCnpj()));
        assertTrue(exception.getMessage().contains("Cannot find seller by CNPJ: "));
    }

    @Test
    @DisplayName("Must find a seller by CNPJ and return it")
    void shouldFindASellerByCnpjAndReturnSellerResponse() {
        when(repository.findByCnpj(seller.getCnpj())).thenReturn(Optional.of(seller));

        var sellerUnderTest = service.findSellerByCnpj(seller.getCnpj());
        verify(repository, times(1)).findByCnpj(seller.getCnpj());

        assertEquals("12345678910", sellerUnderTest.getId());
        assertEquals( "Seller", sellerUnderTest.getName());
        assertEquals("Test", sellerUnderTest.getLastName());
        assertEquals( "123456789100", sellerUnderTest.getPhoneNumber());
        assertEquals( "Testing Street", sellerUnderTest.getAddress());
        assertEquals("seller@email.com", sellerUnderTest.getEmail());
        assertEquals("123", sellerUnderTest.getPassword());
        assertEquals(  "12345678910000", sellerUnderTest.getCnpj());
    }

    @Test
    @DisplayName("Throw an ResourceNotFoundException when deleteById fails")
    void mustThrowResourceNotFoundExceptionWhenDeleteSellerByIdFails() {
        when(repository.findById(seller.getId())).thenReturn(Optional.empty());

        var exception = assertThrows(ResourceNotFoundException.class, () ->
                service.deleteSellerById(seller.getId()));

        verify(repository, never()).deleteById(seller.getId());
        assertTrue(exception.getMessage().contains(seller.getId()));
        assertTrue(exception.getMessage().contains("Seller doesn't exist!"));
    }

    @Test
    @DisplayName("Must find a seller by id, delete it and returns nothing")
    void shouldFindASellerByIdDeleteSellerAndReturnNothing() {
        doNothing().when(repository).deleteById(seller.getId());
        when(repository.findById(seller.getId())).thenReturn(Optional.of(seller));

        service.deleteSellerById(seller.getId());
        verify(repository, times(1)).deleteById(seller.getId());
    }

    @Test
    @DisplayName("Throw ResourceNotFoundException when updateRegisteredSeller fails")
    void mustThrowResourceNotFoundExceptionWhenUpdateSellerByIdFails() {
        when(repository.findById(seller.getId())).thenReturn(Optional.empty());
        SellerUpdateForm form = new SellerUpdateForm();

        var exception = assertThrows(ResourceNotFoundException.class, () ->
                service.updateRegisteredSeller(form, seller.getId()));

        verify(repository, never()).save(any(Seller.class));
        assertTrue(exception.getMessage().contains(seller.getId()));
        assertTrue(exception.getMessage().contains("Seller not registered: " ));
    }

    @Test
    @DisplayName("Must find a seller by id, update it and returns updated seller")
    void shouldFindSellerByIdUpdateSellerAndReturnSellerResponse() {
        SellerUpdateForm form = new SellerUpdateForm(
                "updatedEmail@email.com",
                "123",
                "123456789100",
                "Updated Address"
        );
        when(repository.findById(seller.getId())).thenReturn(Optional.of(seller));
        when(repository.save(seller)).thenReturn(seller);

        var updatedSellerResponse = service.updateRegisteredSeller(form, seller.getId());

        verify(repository, times(1)).save(seller);
        verify(repository, times(1)).findById(seller.getId());

        assertEquals("updatedEmail@email.com", updatedSellerResponse.getEmail());
        assertEquals("123", updatedSellerResponse.getPassword());
        assertEquals("123456789100", updatedSellerResponse.getPhoneNumber());
        assertEquals("Updated Address", updatedSellerResponse.getAddress());
    }

}
