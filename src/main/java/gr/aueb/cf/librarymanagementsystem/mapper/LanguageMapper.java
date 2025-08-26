package gr.aueb.cf.librarymanagementsystem.mapper;

import gr.aueb.cf.librarymanagementsystem.dto.language.LanguageDto;
import gr.aueb.cf.librarymanagementsystem.model.static_data.Language;

public class LanguageMapper {

    public static LanguageDto mapLanguageToDTO(Language language) {
        var dto = new LanguageDto();

        dto.setId(language.getId());
        dto.setName(language.getName());

        return dto;
    }
}
