package ru.otus.jdbc.mapper;

public class EntityClassMetaDataException extends RuntimeException {
    public EntityClassMetaDataException(String msg) {
        super(msg);
    }

    public EntityClassMetaDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
