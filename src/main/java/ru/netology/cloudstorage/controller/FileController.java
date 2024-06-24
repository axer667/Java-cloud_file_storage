package ru.netology.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.netology.cloudstorage.dto.request.NewFileNameRequest;
import ru.netology.cloudstorage.dto.response.FileResponse;
import ru.netology.cloudstorage.mapper.CloudServiceMapper;
import ru.netology.cloudstorage.model.entity.FileEntity;
import ru.netology.cloudstorage.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static ru.netology.cloudstorage.exceptions.MessageConstant.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final CloudServiceMapper mapper;

    @GetMapping(LIST)
    public List<FileResponse> getAllFiles(@RequestHeader("auth-token") String authToken,
                                          @RequestParam("limit") Integer limit) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            return mapper.fileEntityToFileResponse(fileService.getAllFiles(limit), limit);
        } else {
            return mapper.fileEntityToFileResponse(fileService.getFiles(limit), limit);
        }

    }

    @PostMapping(FILE)
    public ResponseEntity<String> uploadFile(@RequestHeader(value = "auth-token") String authToken,
                                             @RequestParam(value = "file") MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String result = fileService.uploadFile(file, authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping(FILE)
    public ResponseEntity<String> deleteFile(@RequestHeader(value = "auth-token") String authToken,
                                             @RequestParam(value = "filename") String filename) {
        String result = fileService.deleteFile(filename);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(FILE)
    public ResponseEntity<byte[]> getFile(@RequestHeader("auth-token") String authToken,
                                          @RequestParam("filename") String filename) {
        FileEntity file = fileService.getFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders
                        .CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.valueOf(file.getType()))
                .body(file.getBody());
    }

    @PutMapping(FILE)
    public ResponseEntity<String> renameFile(@RequestHeader("auth-token") String authToken,
                                             @RequestParam String filename,
                                             @RequestBody NewFileNameRequest newFilename) {
        String result = fileService.renameFile(filename, newFilename);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}