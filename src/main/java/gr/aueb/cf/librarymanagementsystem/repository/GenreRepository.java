package gr.aueb.cf.librarymanagementsystem.repository;

import gr.aueb.cf.librarymanagementsystem.model.static_data.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

}
