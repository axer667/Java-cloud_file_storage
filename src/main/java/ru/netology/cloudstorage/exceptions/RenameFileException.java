package ru.netology.cloudstorage.exceptions;

public class RenameFileException extends RuntimeException {
    public RenameFileException(String message) {
        super(message);
    }
}
