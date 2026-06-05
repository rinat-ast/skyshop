package org.skypro.skyshop;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basketItem.BasketItem;
import org.skypro.skyshop.model.exception.NoSuchProductException;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.service.BasketService;
import org.skypro.skyshop.model.service.StorageService;
import org.skypro.skyshop.model.userBasket.UserBasket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

public class BasketServiceTest {
    private BasketService basketService;
    private ProductBasket mockProductBasket;
    private StorageService mockStorageService;

    @BeforeEach
    void setUp() {
        mockProductBasket = mock(ProductBasket.class);
        mockStorageService = mock(StorageService.class);
        basketService = new BasketService(mockProductBasket, mockStorageService);
    }
    /**
     * Сценарий 1: Добавление несуществующего товара в корзину приводит к выбросу исключения
     */
    @Test
    void addProduct_WhenProductDoesNotExist_ShouldThrowException() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        when(mockStorageService.getProductById(nonExistingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchProductException.class, () -> basketService.addInBasketById(nonExistingId),
                "Добавление несуществующего товара должно вызывать исключение NoSuchProductException"
        );

        // Verify: метод addToBasket не должен вызываться
        verify(mockProductBasket, never()).addToBasket(any(UUID.class));
    }

    /**
     * Сценарий 2: Добавление существующего товара вызывает метод addProduct у мока ProductBasket
     */
    @Test
    void addProduct_WhenProductExists_ShouldCallAddToBasket() {
        // Arrange
        UUID existingId = UUID.randomUUID();
        Product product = new SimpleProduct("Ноутбук", 50000, existingId);
        when(mockStorageService.getProductById(existingId)).thenReturn(Optional.of(product));

        // Act
        basketService.addInBasketById(existingId);

        // Assert: проверяем, что метод addToBasket был вызван с правильным ID
        verify(mockProductBasket, times(1)).addToBasket(existingId);
    }

    /**
     * Сценарий 3: getUserBasket возвращает пустую корзину, если ProductBasket пуст
     */
    @Test
    void getUserBasket_WhenBasketIsEmpty_ShouldReturnEmptyMap() {
        // Arrange: настраиваем мок ProductBasket для возврата пустой корзины
        when(mockProductBasket.getProductBasket()).thenReturn(Collections.emptyMap());

        // Act
        UserBasket result = basketService.getUserBasket();

        // Assert
        assertNotNull(result);
        assertTrue(result.getItems().isEmpty());
        assertEquals(0.0, result.getTotal(), 0.001);
        verify(mockProductBasket, times(1)).getProductBasket();
    }

    /**
     * Сценарий 4: getUserBasket возвращает подходящую корзину, если в ProductBasket есть товары
     */
    @Test
    void getUserBasket_WhenBasketHasProducts_ShouldReturnCorrectMap() {
        // Arrange: создаём тестовые данные для корзины
//        UUID productId1 = UUID.randomUUID();
//        UUID productId2 = UUID.randomUUID();
//
//        Map<UUID, Integer> basketContents = new HashMap<>();
//        basketContents.put(productId1, 2); // 2 единицы товара 1
//        basketContents.put(productId2, 1); // 1 единица товара 2
//
//        when(mockProductBasket.getProductBasket()).thenReturn(basketContents);
//
//        // Act
//        Map<UUID, Integer> result = basketService.getUserBasket();
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertEquals(Integer.valueOf(2), result.get(productId1));
//        assertEquals(Integer.valueOf(1), result.get(productId2));
//        verify(mockProductBasket, times(1)).getProductBasket();
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();
        Product product1 = new SimpleProduct("Товар 1", 1000, productId1);
        Product product2 = new SimpleProduct("Товар 2", 2000, productId2);

        // Настраиваем моки
        when(mockStorageService.getProductById(productId1)).thenReturn(Optional.of(product1));
        when(mockStorageService.getProductById(productId2)).thenReturn(Optional.of(product2));
        when(mockProductBasket.getProductBasket()).thenReturn(Map.of(
                productId1, 2,  // 2 шт. товара 1
                productId2, 1   // 1 шт. товара 2
        ));

        // Act: получаем корзину
        UserBasket result = basketService.getUserBasket();

        // Assert: проверяем содержимое корзины и общую стоимость
        assertNotNull(result);
        assertEquals(2, result.getItems().size());

        List<BasketItem> items = result.getItems();
        BasketItem item1 = items.get(0);
        BasketItem item2 = items.get(1);

        // Проверяем первый товар
        assertEquals(product1, item1.getProduct());
        assertEquals(2, item1.getQuantity());

        // Проверяем второй товар
        assertEquals(product2, item2.getProduct());
        assertEquals(1, item2.getQuantity());

        // Общая стоимость: 2×1000 + 1×2000 = 4000
        assertEquals(4000.0, result.getTotal(), 0.001);
    }
}