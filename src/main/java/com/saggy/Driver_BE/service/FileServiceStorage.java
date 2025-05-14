package com.saggy.Driver_BE.service;


import com.saggy.Driver_BE.entity.FileEntity;
import com.saggy.Driver_BE.repo.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceStorage{

    @Value("${file.upload-dir}")
    private String uploadDir; //application properties theke value nebe

    private  final FileRepository fileRepository;


    //when I have only one parameter , then no need to make autowired
    // we can inject dependancy simply by constructor
    public FileServiceStorage(FileRepository fileRepository){
        this.fileRepository= fileRepository;
    }

    public String saveFile(MultipartFile file,Long parentFolderId)throws IOException {
        //multipart file is the sender's file jeta pathabo
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)){
            //Files class is responsible to communicate with my local storage
            Files.createDirectories(uploadPath);
        }
        //multipart file
        String fileName=file.getOriginalFilename();
        Path filePath=uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);

        //meta data for db
        FileEntity fileEntity=new FileEntity();
        fileEntity.setName(fileName);
        fileEntity.setPath(filePath.toString());
        fileEntity.setSize(file.getSize());//multipart file  e direct size return kore
        fileEntity.setType("file");
        fileEntity.setParentFolderId(parentFolderId);
        fileEntity.setCreatedAt(LocalDateTime.now());


        fileRepository.save(fileEntity);

        return "File uploaded succesfully";
    }

    /*
    home work : place upload logic in try-catch and handle exceptions if occur any
    public String saveFile(MultipartFile file, Long parentFolderId) {
    try {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(fileName);
        fileEntity.setPath(filePath.toString());
        fileEntity.setSize(file.getSize());
        fileEntity.setType("file");
        fileEntity.setParentFolderId(parentFolderId);
        fileEntity.setCreatedAt(LocalDateTime.now());

        fileRepository.save(fileEntity);

        return "File uploaded successfully";

    } catch (IOException e) {
        e.printStackTrace(); // Log it or use a logger in production
        return "Failed to upload file: " + e.getMessage();
    }
}
    */

    public List<FileEntity> getFilesInFolder(Long parentFolderId){
        if(parentFolderId==null){
            return fileRepository.findAll()
                    .stream()
                    .filter(f->f.getParentFolderId()==null)
                    .collect(Collectors.toList());
        }
        else{
            return fileRepository.findAll()
                    .stream()
                    .filter(f->parentFolderId.equals(f.getParentFolderId()))
                    .collect(Collectors.toList());
        }
    }

    public FileEntity getFileById(Long id){
        return fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not Found"));
    }

    public void deleteById(Long id) {
        FileEntity fileEntity = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found with ID: " + id));

        // Optional: Also delete the physical file from disk
        Path filePath = Paths.get(fileEntity.getPath());
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete physical file: " + e.getMessage());
        }

        // Now delete metadata from DB
        fileRepository.deleteById(id);
    }

}
