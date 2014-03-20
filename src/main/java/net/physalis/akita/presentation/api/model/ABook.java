package net.physalis.akita.presentation.api.model;

import net.physalis.akita.domain.model.Book;

public class ABook {

    private final Book book;

    public ABook(Book book) {
        this.book = book;
    }

    public int getId() {
        return book.getId().getValue();
    }

    public String getTitle() {
        return book.getTitle();
    }
}
