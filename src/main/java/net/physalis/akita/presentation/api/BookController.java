package net.physalis.akita.presentation.api;

import net.physalis.akita.domain.model.Book;
import net.physalis.akita.domain.model.BookId;
import net.physalis.akita.domain.repository.BookRepository;
import net.physalis.akita.presentation.csrf.PreventCsrf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@PreventCsrf
@RestController
@RequestMapping("/books")
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Collection<Book> findAll() {
        return bookRepository.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Book find(@PathVariable int id) {
        return bookRepository.findById(new BookId(id));
    }
}
