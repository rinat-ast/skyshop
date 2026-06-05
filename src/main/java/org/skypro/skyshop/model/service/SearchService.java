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
        return storageService.getAllSearchable()
                .stream()
                .filter(searchable -> searchable.getSearchTerm().contains(text))
                .map(SearchResult::fromSearchable)
                .collect(Collectors.toList());
    }
    
}