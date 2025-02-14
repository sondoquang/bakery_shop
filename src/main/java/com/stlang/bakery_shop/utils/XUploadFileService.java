package com.stlang.bakery_shop.utils;

import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class XUploadFileService {

    @Autowired
    ServletContext servletContext;

    public File save(MultipartFile file, String folder){
        File dir = new File(servletContext.getRealPath(folder));
        if (!dir.exists()){
            dir.mkdirs();
        }
        try {
            File saveFile = new File(dir, file.getOriginalFilename());
            file.transferTo(saveFile);
            return saveFile;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
