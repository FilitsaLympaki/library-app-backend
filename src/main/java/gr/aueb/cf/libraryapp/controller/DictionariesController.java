package gr.aueb.cf.libraryapp.controller;

import gr.aueb.cf.libraryapp.dto.dictionary.DictionaryDto;
import gr.aueb.cf.libraryapp.service.DictionariesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dictionaries/")
@Tag(name = "Dictionary Data", description = "Operations for retrieving reference data like genres, languages, and other lookup values")
public class DictionariesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DictionariesController.class);
    private final DictionariesService dictionariesService;

    @Operation(
            summary = "Get all dictionary data",
            description = "Retrieves all reference data used throughout the application, including genres, languages, user roles, and other lookup values. This data is typically used to populate dropdowns and form selections.",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Dictionary data retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DictionaryDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized - Authentication required",
                            content = @Content
                    )
            }
    )
    @GetMapping
    public ResponseEntity<DictionaryDto> getDictionaries() {
        LOGGER.info("Getting dictionaries - Start");

        DictionaryDto dictionaries = dictionariesService.getDictionaries();

        LOGGER.info("Getting dictionaries - End");
        return ResponseEntity.ok(dictionaries);
    }
}
