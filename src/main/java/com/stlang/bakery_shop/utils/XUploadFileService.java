package com.stlang.bakery_shop.utils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class XUploadFileService {

    @Autowired
    ServletContext servletContext;

    public File save(MultipartFile file, String folder) throws IOException {
        File dir = new File(servletContext.getRealPath(folder));
        String uniqueFileName = UUID.randomUUID()+file.getOriginalFilename();
        if (!dir.exists()){
            dir.mkdirs();
        }
        try {
            File saveFile = new File(dir, uniqueFileName);
            file.transferTo(saveFile);
            return saveFile;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        String fileName = file.getOriginalFilename();
//        String uniqueFilename = UUID.randomUUID()+"_"+fileName;
//        Path uploadDir  = Paths.get(folder);
//        if(!Files.exists(uploadDir)){
//            Files.createDirectories(uploadDir);
//        }
//        Path destination = Paths.get(uploadDir.toString(),uniqueFilename);
//        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
//        return uniqueFilename;

    }

}
