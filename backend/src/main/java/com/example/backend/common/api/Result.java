package com.example.backend.common.api;

import java.time.LocalDateTime;

public record Result<T>(
        int code,
        String message,
        T data,
        LocalDateTime timestamp
) {

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data, LocalDateTime.now());
    }

    public static Result<Void> success() {
        return success(null);
    }

    public static <T> Result<T> failure(ResultCode resultCode, String message) {
        return new Result<>(resultCode.getCode(), message, null, LocalDateTime.now());
    }

    public static <T> Result<T> failure(ResultCode resultCode) {
        return failure(resultCode, resultCode.getMessage());
    }
}
