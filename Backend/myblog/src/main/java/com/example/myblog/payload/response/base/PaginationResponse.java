package com.example.myblog.payload.response.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaginationResponse {
    private Object content;
    private int pageCount;
}
