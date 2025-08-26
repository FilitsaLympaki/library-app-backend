package gr.aueb.cf.librarymanagementsystem.model.static_data;

import gr.aueb.cf.librarymanagementsystem.model.AbstractEntity;
import gr.aueb.cf.librarymanagementsystem.model.Book;
import jakarta.persistence.*;
import lombok.*;

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
