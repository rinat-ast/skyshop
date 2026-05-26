package org.skypro.skyshop.model.search;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public interface Searchable {

    String getSearchTerm();

    String getTypeContent();

    @JsonIgnore
    default String getStringRepresentation() {
        return "Имя S " + getSearchTerm() + " тип S " + getTypeContent();
    }

    UUID getId();
}