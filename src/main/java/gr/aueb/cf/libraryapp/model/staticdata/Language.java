package gr.aueb.cf.libraryapp.model.staticdata;

import gr.aueb.cf.libraryapp.model.AbstractEntity;
import gr.aueb.cf.libraryapp.model.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "languages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Language extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "language", cascade = CascadeType.REMOVE)
    private Set<Book> books = new HashSet<>();
}
