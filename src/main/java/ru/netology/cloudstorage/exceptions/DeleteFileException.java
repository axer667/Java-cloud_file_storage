package ru.netology.cloudstorage.exceptions;

public class DeleteFileException extends RuntimeException {
    public DeleteFileException(String message) {
        super(message);
    }
}
