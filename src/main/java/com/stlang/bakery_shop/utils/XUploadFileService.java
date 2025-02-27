package com.stlang.bakery_shop.utils;

import jakarta.servlet.ServletContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class XUploadFileService {

    private final ServletContext servletContext;

    public XUploadFileService(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String save(MultipartFile file, String folder) throws IOException {
        File dir = new File(servletContext.getRealPath(folder));
        String uniqueFileName = UUID.randomUUID()+file.getOriginalFilename();
        if (!dir.exists()){
            dir.mkdirs();
        }
        try {
            File saveFile = new File(dir, uniqueFileName);
            file.transferTo(saveFile);
            return uniqueFileName;
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
