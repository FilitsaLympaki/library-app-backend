package gr.aueb.cf.librarymanagementsystem.service;

import gr.aueb.cf.librarymanagementsystem.core.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);
    private static final String CURRENT_DIR = System.getProperty("user.dir");
    private static final String UPLOAD_DIR = CURRENT_DIR + "/public/images/";

    @Override
    public void deleteImageFile(String fileName) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
            LOGGER.info("Deleted image file: {}", fileName);
        } catch (Exception e) {
            LOGGER.warn("Failed to delete image file: {}", fileName, e);
        }
    }

    @Override
    public String saveBase64Image(String base64Image, String bookTitle) {
        try {
            // Validate base64 string
            if (base64Image == null || base64Image.trim().isEmpty()) {
                throw new IllegalArgumentException("Base64 image data is empty");
            }

            String base64Data = base64Image;
            String fileExtension = "jpg"; // default

            // Extract file type and data
            if (base64Image.startsWith("data:")) {
                String[] parts = base64Image.split(",");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Invalid base64 format");
                }

                // Extract file extension from MIME type
                String mimeType = parts[0];
                if (mimeType.contains("png")) fileExtension = "png";
                else if (mimeType.contains("jpeg") || mimeType.contains("jpg")) fileExtension = "jpg";
                else if (mimeType.contains("gif")) fileExtension = "gif";

                base64Data = parts[1];
            }

            // Decode and validate
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);
            if (imageBytes.length == 0) {
                throw new IllegalArgumentException("Decoded image is empty");
            }

            // Generate unique filename
            String fileName = bookTitle + "_" + System.currentTimeMillis() + "." + fileExtension;

            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save file
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.write(filePath, imageBytes);

            LOGGER.info("Successfully saved image: {}", fileName);
            return fileName;

        } catch (Exception e) {
            LOGGER.error("Failed to save base64 image");
            throw new RuntimeException("Failed to save image: " + e.getMessage(), e);
        }
    }

    @Override
    public Resource getImage(String filename) throws ResourceNotFoundException, MalformedURLException {

            Path filePath = Paths.get(UPLOAD_DIR).resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("Resource", "Filename " + filename + " does not exists");
            }
    }
}
