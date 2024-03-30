package com.example.myblog.payload.response.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseResponse {
    private int statusCode = 200;
    private String message = "";
    private Object data = "";
}
