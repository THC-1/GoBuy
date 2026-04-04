package com.example.backend.common.api;

import java.util.List;

public record PageResponse<T>(
        long total,
        long pageNum,
        long pageSize,
        List<T> records
) {
}
