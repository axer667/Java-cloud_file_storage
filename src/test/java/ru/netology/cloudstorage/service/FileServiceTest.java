package ru.netology.cloudstorage.service;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.dto.request.NewFileNameRequest;
import ru.netology.cloudstorage.exceptions.DeleteFileException;
import ru.netology.cloudstorage.model.entity.FileEntity;
import ru.netology.cloudstorage.model.entity.UserEntity;
import ru.netology.cloudstorage.repository.FileRepository;
import org.mockito.*;
import ru.netology.cloudstorage.repository.UserRepository;
import ru.netology.cloudstorage.security.TokenManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static ru.netology.cloudstorage.exceptions.MessageConstant.*;

public class FileServiceTest {
    private final static String USERNAME = "USER";
    private static FileRepository fileRepository;
    private static UserRepository userRepository;
    private static FileServiceImpl fileService;
    private static FileEntity file;

    @BeforeEach
    public  void beforeEach() {
        UserEntity mockedCloudUser = Mockito.mock(UserEntity.class);
        TokenManager tokenManager = Mockito.mock(TokenManager.class);
        Mockito.when(tokenManager.extractEmailFromJwt(Mockito.anyString())).thenReturn(USERNAME);

        fileRepository = Mockito.mock(FileRepository.class);
        userRepository = Mockito.mock(UserRepository.class);

        when(tokenManager.generateToken(Mockito.<UserDetails>any())).thenReturn("testToken");
        byte[] fileBytes = "content".getBytes();
        file = new FileEntity( "TestFile", mockedCloudUser,256,"image/jpeg", fileBytes);
        when(fileRepository.findByName(Mockito.any()))
                .thenReturn(
                        file
                );
        fileService = new FileServiceImpl(fileRepository, userRepository);
    }

    @Test
    public void downloadOK() {
        FileEntity fileEntity = fileService.getFile("TestFile");
        Assert.assertNotNull(fileEntity);
        Assertions.assertEquals(file, fileEntity);
    }

    @Test
    public void uploadOK() throws IOException {
        UserEntity mockedCloudUser = Mockito.mock(UserEntity.class);
        Mockito.when(userRepository.getUsersByLogin(Mockito.anyString()))
                .thenReturn(Optional.of(mockedCloudUser));
        Mockito.when(fileRepository.findByName(Mockito.anyString())).thenReturn(null);

        File file = new File("test.txt");
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", fileBytes);
        String actual = fileService.uploadFile(multipartFile, "user");
        String expected = SUCCESS_UPLOAD;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void deleteOK() throws DeleteFileException {
        String actual = fileService.deleteFile("TestFile");
        String expected = SUCCESS_DELETE;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void editOK(){
        NewFileNameRequest newFileNameRequest = new NewFileNameRequest("RENAMED");
        String expected = SUCCESS_RENAME;
        String actual = fileService.renameFile(file.getName(), newFileNameRequest);
        Assertions.assertEquals(expected, actual);
    }
}