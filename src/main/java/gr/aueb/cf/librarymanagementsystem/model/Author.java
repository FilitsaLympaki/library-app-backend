package gr.aueb.cf.librarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Author extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String biography;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "death_date")
    private Date deathDate;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private Set<Book> books = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_updated_id")
    private User userUpdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_inserted_id")
    private User userInsert;

}
