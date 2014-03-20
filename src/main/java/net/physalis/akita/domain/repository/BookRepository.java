package net.physalis.akita.domain.repository;

import net.physalis.akita.domain.model.Book;
import net.physalis.akita.domain.model.BookId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepository {

    private final JdbcTemplate JT;

    @Autowired
    public BookRepository(JdbcTemplate JT) {
        this.JT = JT;
    }

    public Book findById(BookId id) {
        return JT.queryForObject("select * from book where id = ?",
                new Object[]{id.getValue()},
                (rs, rowNum) -> new Book(
                        new BookId(rs.getInt("id")),
                        rs.getString("title")
                )
        );
    }

    public List<Book> findAll() {
        return JT.query("select id from book order by id",
                (rs, rowNum) -> findById(new BookId(rs.getInt("id"))));
    }

    public Book save(Book book) {
        try {
            JT.update("insert into book (id, title) values (?, ?)",
                    book.getId(), book.getTitle());
        } catch (DuplicateKeyException e) {
            JT.update("update book set title = ? where id = ?", book.getTitle(), book.getId().getValue());
        }

        return book;
    }

    public void delete(BookId id) {
        JT.update("delete from book where id = ?", id.getValue());
    }

}
