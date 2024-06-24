package ru.netology.cloudstorage.dto.response;
public record FileResponse(
        String filename,
        long size,
        String username) {}