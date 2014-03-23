package net.physalis.akita.presentation.api;

import net.physalis.akita.domain.model.Book;
import net.physalis.akita.domain.model.BookId;
import net.physalis.akita.domain.repository.BookRepository;
import net.physalis.akita.presentation.api.model.ABook;
import net.physalis.akita.presentation.csrf.PreventCsrf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
  public List<ABook> findAll() {
    return bookRepository.findAll().stream().map(ABook::new).collect(Collectors.toList());
  }

  @RequestMapping(value = "{id}", method = RequestMethod.GET)
  public ResponseEntity<ABook> find(@PathVariable int id) {
    Optional<Book> book = bookRepository.findById(new BookId(id));
    if (book.isPresent()) {
      return new ResponseEntity<>(new ABook(book.get()), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
