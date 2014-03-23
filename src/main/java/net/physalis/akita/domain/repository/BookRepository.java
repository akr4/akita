package net.physalis.akita.domain.repository;

import net.physalis.akita.domain.model.Book;
import net.physalis.akita.domain.model.BookId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BookRepository {

  private final JdbcTemplate JT;

  @Autowired
  public BookRepository(JdbcTemplate JT) {
    this.JT = JT;
  }

  public Optional<Book> findById(BookId id) {
    try {
      return JT.queryForObject("select * from book where id = ?",
          new Object[]{id.getValue()},
          (rs, rowNum) -> Optional.of(new Book(
              new BookId(rs.getInt("id")),
              rs.getString("title")
          ))
      );
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public List<Book> findAll() {
    return JT.query("select id from book order by id",
        (rs, rowNum) -> findById(new BookId(rs.getInt("id"))))
        .stream().filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
  }

  public Book create(String title) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    JT.update(conn -> {
      PreparedStatement ps = conn.prepareStatement("insert into book (title) values (?)");
      ps.setString(1, title);
      return ps;
    }, keyHolder);

    return findById(new BookId(keyHolder.getKey().intValue())).get();
  }

  public void delete(BookId id) {
    JT.update("delete from book where id = ?", id.getValue());
  }

}
