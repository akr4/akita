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

    public Book findById(int id) {
        return JT.queryForObject("select * from book where id = ?",
                new Object[]{id},
                (rs, rowNum) -> new Book(
                        new BookId(rs.getInt("id")),
                        rs.getString("title")
                )
        );
    }

    public List<Book> findAll() {
        return JT.query("select id from book order by id",
                (rs, rowNum) -> findById(rs.getInt("id")));
    }

    public Book save(Book book) {
        try {
            JT.update("insert into book (id, title) values (?, ?)",
                    book.getId(), book.getTitle());
        } catch (DuplicateKeyException e) {
            JT.update("update book set title = ?", book.getTitle());
        }

        return book;
    }

    public void delete(Book book) {
        JT.update("delete from book where id = ?", book.getId());
    }

}
