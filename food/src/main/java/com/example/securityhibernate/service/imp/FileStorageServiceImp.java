package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.listenum.FolderType;
import com.example.securityhibernate.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageServiceImp implements FileStorageService {

    @Value("${fileStorage.path}")
    private String parentFolder;

    private Path parentRoot;

    private String childFolder;

    private Path childRoot;

    private void initParent(){
        try {
            parentRoot =  Paths.get(parentFolder);
            if(!Files.exists(parentRoot)){
                Files.createDirectories(parentRoot);
            }
        } catch (Exception e) {
            System.out.println("Error create folder parent: " + e.getMessage());
        }
    }

    private void setChildRoot(FolderType folderType){
        childFolder = parentFolder + "/" + folderType.toString();
        childRoot =  Paths.get(childFolder);
    }

    private void initChild(FolderType folderType){
        try {
            setChildRoot(folderType);
            if(!Files.exists(childRoot)){
                Files.createDirectories(childRoot);
            }
        } catch (Exception e) {
            System.out.println("Error create folder child: "  + e.getMessage());
        }
    }

    @Override
    public boolean saveFiles(MultipartFile file, String newFileName, FolderType folderType) {
        try {
            initParent();
            initChild(folderType);
            Files.copy(file.getInputStream(), childRoot.resolve(newFileName), StandardCopyOption.REPLACE_EXISTING);
            return true;
        }catch (Exception e){
            System.out.println("Error save file" + file.getOriginalFilename()  + e.getMessage());
            return false;
        }
    }

    @Override
    public Resource load(String fileName, FolderType folderType) {
        try {
            setChildRoot(folderType);
            Path file = childRoot.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists()|| resource.isReadable()){
                return resource;
            }else {
                return null;
            }
        }catch (Exception e){
            System.out.println("Error load file: " + fileName  + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean removeFile(String fileName, FolderType folderType) {
        try {
            initParent();
            initChild(folderType);
            Path pathFile = childRoot.resolve(fileName);
            Files.delete(pathFile);
            return true;
        }catch (Exception e){
            System.out.println("Error at remove file" + e.getMessage());
            return false;
        }
    }

}
