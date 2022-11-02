package dev.aabstractt.punishments.datasource;

import lombok.NonNull;

public final class DataSourceException extends RuntimeException {

    public DataSourceException(@NonNull String message) {
        super(message);
    }
}