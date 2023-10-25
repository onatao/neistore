package com.neidev.store.product.service;

import com.neidev.store.domain.core.product.json.ProductUpdateForm;
import com.neidev.store.domain.core.product.model.Product;
import com.neidev.store.domain.core.product.json.ProductResponse;
import com.neidev.store.domain.handler.exceptions.ProdutAlreadyRegisteredException;
import com.neidev.store.domain.handler.exceptions.ResourceNotFoundException;
import com.neidev.store.domain.repository.ProductRepository;
import com.neidev.store.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(
                "12345678910",
                "Product Test",
                "Description",
                "Short Description",
                "Image",
                new BigDecimal(10)
        );
    }

    @Test
    @DisplayName("Throw ProductAlreadyRegisteredException when try to create product")
    void mustThrowProductAlreadyRegisteredExceptionWhenCreateProductFails() {
        when(repository.findByProductName(product.getProductName().toUpperCase())).thenReturn(Optional.of(product));

        var exception = assertThrows(ProdutAlreadyRegisteredException.class, () ->
                service.create(product));

        verify(repository, never()).save(product);
        assertTrue(exception.getMessage().contains(product.getProductName()));
        assertTrue(exception.getMessage().contains("Product Test"));
        assertTrue(exception.getMessage().contains("Product already registered: "));
    }

    @Test
    @DisplayName("Create a new product and return created product")
    void shouldCreateAProductAndReturnCreatedProductWithoutAnyProblem() {
        when(repository.save(any(Product.class))).thenReturn(product);

        var persistedProduct = service.create(product);

        verify(repository, times(1)).save(product);
        assertEquals(persistedProduct.getClass(), ProductResponse.class);

        assertEquals("12345678910", persistedProduct.getId());
        assertEquals("PRODUCT TEST", persistedProduct.getProductName());
        assertEquals("Short Description", persistedProduct.getShortDescription());
        assertEquals(new BigDecimal(10), persistedProduct.getPrice());
    }

    @Test
    @DisplayName("Throw ResourceNotFoundException when deleteById fails")
    void mustThrowResourceNotFoundExceptionWhenTryToDeleteProductById() {
        when(repository.findById(product.getId())).thenReturn(Optional.empty());

        var exception = assertThrows(ResourceNotFoundException.class, () ->
                service.deleteById(product.getId()));

        assertTrue(exception.getMessage().contains(product.getId()));
        assertTrue(exception.getMessage().contains("Product isn't registered: "));
    }

    @Test
    @DisplayName("Must delete a product by ID and returns nothing")
    void shouldFindProductByIdDeleteThatProductAndReturnNothing() {
        when(repository.findById(product.getId())).thenReturn(Optional.of(product));
        doNothing().when(repository).deleteById(product.getId());

        service.deleteById(product.getId());
        verify(repository, times(1)).findById(product.getId());
        verify(repository, times(1)).deleteById(product.getId());
    }

    @Test
    @DisplayName("Throw ResourceNotFoundException when findAll fails")
    void mustThrowResourceNotFoundExceptionWhenFindAllProductsFails() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        var exception = assertThrows(ResourceNotFoundException.class, () ->
                service.findAll());
        assertTrue(exception.getMessage().contains("Cannot retrieve product list"));
    }

    @Test
    @DisplayName("Must find all registered products and return list containing it")
    void shouldFindAllProductsAndReturnAnProductResponseList() {
        Product product1 = new Product(
                "12345678911",
                "Product Test 1",
                "Description 1",
                "Short Description 1",
                "Image 1",
                new BigDecimal(10)
        );
        List<Product> repositoryExpectedList = Arrays.asList(product, product1);
        when(repository.findAll()).thenReturn(repositoryExpectedList);

        List<ProductResponse> responseList = service.findAll();
        verify(repository, times(1)).findAll();

        assertNotNull(responseList);
        responseList.stream().forEach(o ->
                assertTrue(o instanceof ProductResponse)
        );
    }

    @Test
    @DisplayName("Throw ResourceNotFoundException when cannot find product by id")
    void mustThrowResourceNotFoundExceptionWhenFindByIdFails() {
        when(repository.findById(product.getId())).thenReturn(Optional.empty());

        var exception = assertThrows(ResourceNotFoundException.class,
                () -> service.findById(product.getId())
        );
        verify(repository, times(1)).findById(product.getId());

        assertTrue(exception.getMessage().contains("Product not registered: "));
        assertTrue(exception.getMessage().contains(product.getId()));
    }

    @Test
    @DisplayName("Must find one product by id and return it")
    void shouldFindOneProductByIdAndReturnProductResponse() {
        when(repository.findById(product.getId())).thenReturn(Optional.of(product));

        var productResponse = service.findById(product.getId());
        verify(repository, times(1)).findById(product.getId());

        assertNotNull(productResponse);
        assertTrue(productResponse instanceof ProductResponse);
        assertEquals(productResponse.getProductName(), "Product Test");
    }

    @Test
    @DisplayName("Throw ResourceNotFoundException when update by id fails")
    void mustThrowResourceNotFoundExceptionWhenUpdateProductByIdFails() {
        when(repository.findById(product.getId())).thenReturn(Optional.empty());
        ProductUpdateForm form = new ProductUpdateForm();

        var exception = assertThrows(ResourceNotFoundException.class,
                () -> service.updateById(form, product.getId())
        );

        verify(repository, never()).save(product);
        assertTrue(exception.getMessage().contains("Product not registered! "));
        assertTrue(exception.getMessage().contains(product.getId()));
    }

    @Test
    @DisplayName("Must find a product by id, updated ir and return product response")
    void shouldFindAProductByIdUpdateThatProductAndReturnProductResponse() {
        ProductUpdateForm form = new ProductUpdateForm(
                "Updated Product Name",
                "Updated Product Description",
                "Updated Product Short Description",
                "Updated Image Url",
                new BigDecimal(10)
        );
        when(repository.findById(product.getId())).thenReturn(Optional.of(product));
        when(repository.save(product)).thenReturn(product);

        var updatedProductResponse = service.updateById(form, product.getId());

        verify(repository, times(1)).findById(product.getId());
        verify(repository, times(1)).save(product);

        assertEquals("UPDATED PRODUCT NAME", updatedProductResponse.getProductName());
        assertEquals("Updated Product Short Description", updatedProductResponse.getShortDescription());
        assertEquals(new BigDecimal(10), updatedProductResponse.getPrice());
    }

}
