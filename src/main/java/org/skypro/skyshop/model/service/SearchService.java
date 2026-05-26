package org.skypro.skyshop.model.service;

import org.skypro.skyshop.model.searchResult.SearchResult;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final StorageService storageService;

    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }

    public  List<SearchResult> search(String text) {
        return storageService.getAllSearchable()//List<Searchable> тут запускается конструктор?
                .stream()
                .filter(searchable -> searchable.getSearchTerm().contains(text))
                .map(SearchResult::fromSearchable)
                .collect(Collectors.toList());
    }
    public static void main(String[] args) {
        StorageService storageService = new StorageService();
        System.out.println("storageService.getAllProducts() = " + storageService.getAllProducts());
        System.out.println("storageService.getAllArticles() = " + storageService.getAllArticles());
        System.out.println("storageService.getAllSearchable() = " + storageService.getAllSearchable());
        SearchService searchService = new SearchService(storageService);
        System.out.println("searchService.search(\"Папа\") = " + searchService.search("Папа"));
    }
}