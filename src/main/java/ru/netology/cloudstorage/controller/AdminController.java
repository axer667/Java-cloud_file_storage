package ru.netology.cloudstorage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.netology.cloudstorage.dto.response.FileResponse;
import ru.netology.cloudstorage.mapper.CloudServiceMapper;
import ru.netology.cloudstorage.service.FileService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final FileService fileService;
    private final CloudServiceMapper mapper;
    @GetMapping("/")
    public List<FileResponse> getAllFiles(@RequestHeader("auth-token") String authToken,
                                          @RequestParam("limit") Integer limit) {
        return mapper.fileEntityToFileResponse(fileService.getAllFiles(limit), limit);
    }
}
