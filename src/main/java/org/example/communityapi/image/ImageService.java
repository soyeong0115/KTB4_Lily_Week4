package org.example.communityapi.image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String upload(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("invalid_request");
        }

        // 사용자가 업로드한 원본 파일명
        String originalFilename = image.getOriginalFilename();

        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new IllegalArgumentException("invalid_request");
        }

        // 확장자 분리
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        String storedFilename = UUID.randomUUID() + "." + extension;

        try {
            Path uploadPath = Paths.get(uploadDir)
                    .toAbsolutePath()
                    .normalize();

            Files.createDirectories(uploadPath);

            // 최종 저장 경로
            Path targetPath = uploadPath.resolve(storedFilename);

            image.transferTo(targetPath.toFile());

            return "/uploads/" + storedFilename;
        } catch (IOException e) {
            throw new RuntimeException("file_upload_failed");
        }
    }
}
