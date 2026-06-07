package org.skypro.skyshop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.search.Searchable;
import org.skypro.skyshop.model.searchResult.SearchResult;
import org.skypro.skyshop.model.service.SearchService;
import org.skypro.skyshop.model.service.StorageService;

import java.util.List;

//import static jdk.internal.classfile.impl.verifier.VerifierImpl.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {
    @InjectMocks
    private SearchService searchService;
    @Mock
    private StorageService mockSt;

//    @BeforeEach
//    void setUp() {
//        mockSt = mock(StorageService.class);
//        searchService = new SearchService(mockSt);
//    }
    /**
     * Сценарий 1: Поиск в случае отсутствия объектов в StorageService.
     */

    @Test
    void search_WhenStorageReturnsEmptyList_ShouldReturnEmptyList() {
        // тут наш мок запрашивает список
        when(mockSt.getAllSearchable()).thenReturn(List.of());
        // запуск поиска
        List<SearchResult> result = searchService.search("любой запрос");

        // Assert проверка на пустой спиок
        assertNotNull(result, "Результат не должен быть null");
        assertTrue(result.isEmpty(), "Результат должен быть пустым списком");
        assertEquals(0, result.size(), "Размер списка должен быть 0");
        //проверка, что метод выполнился
        verify(mockSt, times(1)).getAllSearchable();
    }

    /**
     * Сценарий 2: Поиск в случае, если объекты в StorageService есть, но нет подходящего.
     */

    @Test
    void search_WhenNoMatchingObjects_ShouldReturnEmptyList() {
        // создание моков
        Searchable searchable1 = mock(Searchable.class);
        Searchable searchable2 = mock(Searchable.class);

        when(searchable1.getSearchTerm()).thenReturn("яблоко");
        when(searchable2.getSearchTerm()).thenReturn("банан");

        List<Searchable> searchables = List.of(searchable1, searchable2);
        when(mockSt.getAllSearchable()).thenReturn(searchables);

        // Act: ищем термин, которого нет в данных
        List<SearchResult> result = searchService.search("апельсин");

        // Assert
        assertNotNull(result, "Результат не должен быть null");
        assertTrue(result.isEmpty(), "Результат должен быть пустым списком при отсутствии совпадений");
        //проверка, что метод выполнился
        verify(mockSt, times(1)).getAllSearchable();
    }

    /**
     * Сценарий 3: Поиск, когда есть подходящий объект в StorageService.
     */
    @Test
    void search_WhenMatchingObjectExists_ShouldReturnMatchingResults() {

        Searchable matchingSearchable = mock(Searchable.class);
        Searchable nonMatchingSearchable = mock(Searchable.class);

        when(matchingSearchable.getSearchTerm()).thenReturn("Ноутбук");
        when(nonMatchingSearchable.getSearchTerm()).thenReturn("Телефон");

        List<Searchable> searchables = List.of(matchingSearchable, nonMatchingSearchable);
        when(mockSt.getAllSearchable()).thenReturn(searchables);

        // Мокируем преобразование Searchable → SearchResult
        SearchResult expectedResult = mock(SearchResult.class);

        try (MockedStatic<SearchResult> mockedStatic = mockStatic(SearchResult.class)) {
            // Настраиваем поведение статического метода
            mockedStatic.when(() -> SearchResult.fromSearchable(matchingSearchable))
                    .thenReturn(expectedResult);
            //вызов метода с неполным текстом поиска
            List<SearchResult> result = searchService.search("Ноу");
//        when(SearchResult.fromSearchable(matchingSearchable)).thenReturn(expectedResult);
            // Assert
            assertNotNull(result, "Результат не должен быть null");
            assertEquals(1, result.size(), "Должен быть возвращён ровно один элемент");
            assertEquals(expectedResult, result.get(0), "Элемент в результате должен соответствовать ожидаемому");
        }
    }
}


