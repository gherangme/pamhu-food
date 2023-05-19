package com.example.securityhibernate.service.imp;

import com.example.securityhibernate.listenum.ImageFolderType;
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

    private void setChildRoot(ImageFolderType imageFolderType){
        childFolder = parentFolder + "/" + imageFolderType.toString();
        childRoot =  Paths.get(childFolder);
    }

    private void initChild(ImageFolderType imageFolderType){
        try {
            setChildRoot(imageFolderType);
            if(!Files.exists(childRoot)){
                Files.createDirectories(childRoot);
            }
        } catch (Exception e) {
            System.out.println("Error create folder child: "  + e.getMessage());
        }
    }

    @Override
    public boolean saveFiles(MultipartFile file, String newFileName, ImageFolderType imageFolderType) {
        try {
            initParent();
            initChild(imageFolderType);
            Files.copy(file.getInputStream(), childRoot.resolve(newFileName), StandardCopyOption.REPLACE_EXISTING);
            return true;
        }catch (Exception e){
            System.out.println("Error save file" + file.getOriginalFilename()  + e.getMessage());
            return false;
        }
    }

    @Override
    public Resource load(String fileName, ImageFolderType imageFolderType) {
        try {
            setChildRoot(imageFolderType);
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
    public boolean removeFile(String fileName, ImageFolderType imageFolderType) {
        try {
            initParent();
            initChild(imageFolderType);
            Path pathFile = childRoot.resolve(fileName);
            Files.delete(pathFile);
            return true;
        }catch (Exception e){
            System.out.println("Error at remove file" + e.getMessage());
            return false;
        }
    }

}
