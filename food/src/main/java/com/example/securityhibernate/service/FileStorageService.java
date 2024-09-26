package com.example.securityhibernate.service;

import com.example.securityhibernate.enums.ImageFolderType;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    boolean saveFiles(MultipartFile file, String newFileName, ImageFolderType imageFolderType);

    Resource load(String fileName, ImageFolderType imageFolderType);

    boolean removeFile(String fileName, ImageFolderType imageFolderType);
}
