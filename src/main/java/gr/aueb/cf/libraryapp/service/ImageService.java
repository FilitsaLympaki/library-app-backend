package gr.aueb.cf.libraryapp.service;

import gr.aueb.cf.libraryapp.core.exceptions.ResourceNotFoundException;
import org.springframework.core.io.Resource;

import java.net.MalformedURLException;

public interface ImageService {

    void deleteImageFile(String fileName);

    String saveBase64Image(String base64Image, String bookTitle);

    Resource getImage(String filename) throws ResourceNotFoundException, MalformedURLException;
}
