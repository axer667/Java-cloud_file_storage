package ru.netology.cloudstorage.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.netology.cloudstorage.exceptions.*;
import ru.netology.cloudstorage.dto.request.NewFileNameRequest;
import ru.netology.cloudstorage.model.entity.FileEntity;
import ru.netology.cloudstorage.model.entity.UserEntity;
import ru.netology.cloudstorage.repository.FileRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.netology.cloudstorage.repository.UserRepository;
import ru.netology.cloudstorage.security.CloudUserDetails;

import java.io.IOException;
import java.util.List;

import static ru.netology.cloudstorage.exceptions.MessageConstant.*;
import static java.util.Objects.isNull;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    @Override
    public List<FileEntity> getFiles(int limit) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CloudUserDetails userDetails = (CloudUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        List<FileEntity> fileList = fileRepository.getFiles(limit, userId);
        return fileList;
    }

    public List<FileEntity> getAllFiles(int limit) {
        List<FileEntity> fileList = fileRepository.findAll();
        return fileList;
    }

    @Override
    public String uploadFile(MultipartFile file, String userName) {
        try {
            UserEntity userEntity = userRepository.getUsersByLogin(userName).orElseThrow(()
                    -> new LoginException(LOGIN_NOT_FOUND_USER));

            FileEntity doublicateFilename = fileRepository.findByName(file.getOriginalFilename());
            if (isNull(doublicateFilename)) {
                fileRepository.save(new FileEntity(file.getOriginalFilename(), userEntity,
                        file.getSize(), file.getContentType(), file.getBytes()));
                log.info("Success upload file: " + file.getOriginalFilename());
                return SUCCESS_UPLOAD;
            }
            log.error("Error upload file: " + file.getOriginalFilename() + ". Filename is not unique!");
            throw new FileUploadException(ERROR_UPLOAD_NOT_UNIQUE_FILE);
        } catch (IOException e) {
            log.error("Error upload file: " + file.getOriginalFilename() + ". Try again.");
            throw new FileUploadException(ERROR_UPLOAD_FILE);
        }
    }

    @Override
    public String deleteFile(String filename) {
        FileEntity file = fileRepository.findByName(filename);
        if (isNull(file)) {
            throw new DeleteFileException(ERROR_DELETE_FILENAME);
        }
        fileRepository.delete(file);
        return SUCCESS_DELETE;
    }

    @Override
    public FileEntity getFile(String filename) {
        return fileRepository.findByName(filename);
    }

    @Override
    public String renameFile(String filename, NewFileNameRequest nf) {
        FileEntity file = fileRepository.findByName(filename);
        if (isNull(file)) {
            log.error("Error rename file: " + filename + ". Try again.");
            throw new RenameFileException(ERROR_RENAME_FILENAME);
        }
        file.setName(nf.filename());
        fileRepository.save(file);
        log.info("Success rename file: " + filename);
        return SUCCESS_RENAME;
    }
}