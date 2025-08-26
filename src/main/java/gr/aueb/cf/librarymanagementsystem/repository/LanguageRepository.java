package gr.aueb.cf.librarymanagementsystem.repository;

import gr.aueb.cf.librarymanagementsystem.model.static_data.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

}

