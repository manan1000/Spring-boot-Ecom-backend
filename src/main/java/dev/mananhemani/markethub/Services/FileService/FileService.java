package dev.mananhemani.markethub.Services.FileService;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    public String uploadImage(String path, MultipartFile image) throws IOException;
}
