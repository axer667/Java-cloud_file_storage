package ru.netology.cloudstorage.mapper;

import ru.netology.cloudstorage.dto.response.FileResponse;
import ru.netology.cloudstorage.model.entity.FileEntity;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CloudServiceMapperImpl implements CloudServiceMapper {

    @Override
    public List<FileResponse> fileEntityToFileResponse(List<FileEntity> list, Integer limit) {
        return list.stream().map(f -> new FileResponse(f.getName(), f.getSize(), f.getUser().getLogin()))
                .limit(limit)
                .collect(Collectors.toList());
    }
}