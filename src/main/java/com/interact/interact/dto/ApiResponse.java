package com.interact.interact.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponse {
    private String message;
    private int statusCode;
    private boolean success;

    public ApiResponse(String message, String user_not_found, int i, boolean b) {
    }
}
