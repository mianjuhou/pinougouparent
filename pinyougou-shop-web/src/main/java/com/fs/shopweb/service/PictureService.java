package com.fs.shopweb.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class PictureService {
    @Autowired
    private FastFileStorageClient fileStorageClient;
    @Value("${fastDfs.host}")
    private String hostIp;

    public String uploadPic(MultipartFile file) {
        try {
            StorePath storePath = fileStorageClient.uploadFile(
                    file.getInputStream(),
                    file.getSize(),
                    FilenameUtils.getExtension(file.getOriginalFilename()), null);
            String fileUrl = hostIp + storePath.getFullPath();
            log.info("上传地址为{}", fileUrl);
            return fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deletePic(String path) {
        if (path.startsWith("http://")) {
            path = path.substring(hostIp.length());
        }
        fileStorageClient.deleteFile(path);
    }
}
