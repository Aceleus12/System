package pl.edu.wat.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wat.demo.entities.FileEntity;
import pl.edu.wat.demo.repositories.FileRepository;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public FileEntity storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
            }

            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(fileName);
            fileEntity.setFileType(file.getContentType());
            fileEntity.setData(file.getBytes());

            return fileRepository.save(fileEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FileEntity getFile(String fileId) {
        if(fileRepository.findById(fileId).isPresent()){
            return fileRepository.findById(fileId).get();
        }
        else {
            return null;
        }



    }
}
