package com.bookmanager.Config;

import com.bookmanager.Exception.RException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class WriteErrorResponse {
    public static void writeErrorResponse(HttpServletResponse response, RException exception) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(
                new ObjectMapper().writeValueAsString(
                        Map.of(
                                "code", exception.getErrorCode().getCode(),
                                "message", exception.getErrorCode().getMessage()
                        )
                )
        );
    }
}
