package gr.aueb.cf.librarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "publishers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Publisher extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String city;
    private String country;
    private String email;
    private Date year;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.REMOVE)
    private Set<Book> books = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_updated_id")
    private User userUpdated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_inserted_id")
    private User userInserted;

    public Publisher(String name) {
        this.name = name;
    }
}
