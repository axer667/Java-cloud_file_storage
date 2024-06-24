package ru.netology.cloudstorage.mapper;

import ru.netology.cloudstorage.dto.response.FileResponse;
import ru.netology.cloudstorage.model.entity.FileEntity;

import java.util.List;

public interface CloudServiceMapper {
    List<FileResponse> fileEntityToFileResponse(List<FileEntity> list, Integer limit);
}