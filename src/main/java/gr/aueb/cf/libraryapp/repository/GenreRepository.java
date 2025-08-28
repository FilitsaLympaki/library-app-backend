package gr.aueb.cf.libraryapp.repository;

import gr.aueb.cf.libraryapp.model.staticdata.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

}
