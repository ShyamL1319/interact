package com.interact.interact.impl;

import com.interact.interact.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
@Slf4j
@Component
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //File Name
        String name = file.getOriginalFilename();
        //abc.png

        log.info("File Name == >>>>>"+name);
        String randomId = UUID.randomUUID().toString();
        assert name != null;
        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));
        File f = new File(path);
        if(!f.exists()){
           boolean b =  f.mkdir();
           log.info("Directory has been created>>>>>>"+f.getPath());
        }

        //Files.copy(file.getInputStream(), Paths.get(filePath));
        Files.write(Paths.get(path,fileName), file.getBytes());
        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path = path+"/"+fileName;
        return new FileInputStream(fullPath);
    }
}
