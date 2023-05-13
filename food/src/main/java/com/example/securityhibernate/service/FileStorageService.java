package com.example.securityhibernate.service;

import com.example.securityhibernate.listenum.FolderType;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    boolean saveFiles(MultipartFile file, String newFileName, FolderType folderType);

    Resource load(String fileName, FolderType folderType);

    boolean removeFile(String fileName, FolderType folderType);
}
