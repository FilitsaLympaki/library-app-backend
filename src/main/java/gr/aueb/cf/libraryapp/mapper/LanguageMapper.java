package gr.aueb.cf.libraryapp.mapper;

import gr.aueb.cf.libraryapp.dto.language.LanguageDto;
import gr.aueb.cf.libraryapp.model.staticdata.Language;

public class LanguageMapper {

    public static LanguageDto mapLanguageToDTO(Language language) {
        var dto = new LanguageDto();

        dto.setId(language.getId());
        dto.setName(language.getName());

        return dto;
    }
}
