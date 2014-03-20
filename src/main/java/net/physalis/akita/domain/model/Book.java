package net.physalis.akita.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(of = "id")
@AllArgsConstructor
public class Book {

    @Getter private final BookId id;
    @Getter private final String title;

}
