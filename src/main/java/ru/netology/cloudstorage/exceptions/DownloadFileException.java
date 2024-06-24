package ru.netology.cloudstorage.exceptions;

public class DownloadFileException extends RuntimeException {
    public DownloadFileException(String message) {
        super(message);
    }
}