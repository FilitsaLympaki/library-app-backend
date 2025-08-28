package gr.aueb.cf.libraryapp.controller;

import gr.aueb.cf.libraryapp.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@Tag(name = "Image Management",
        description = "Operations for retrieving book cover images and other static image resources")
public class ImageController {

    private final ImageService imageService;

    @Operation(
            summary = "Get image by filename",
            description = "Retrieves an image file by its filename. Returns the image as JPEG format.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Image retrieved successfully",
                            content = @Content(
                                    mediaType = "image/jpeg",
                                    schema = @Schema(
                                            type = "string",
                                            format = "binary",
                                            description = "Image file"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Image not found - File does not exist or is not readable",
                            content = @Content
                    )
            }
    )
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {

        try {
            Resource resource = imageService.getImage(filename);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
