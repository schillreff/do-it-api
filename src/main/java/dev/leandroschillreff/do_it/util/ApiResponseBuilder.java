package dev.leandroschillreff.do_it.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class ApiResponseBuilder {

    private ApiResponseBuilder() {
    }

    private static final String TIMESTAMP_KEY = "timestamp";
    private static final String STATUS_KEY = "status";
    private static final String MESSAGE_KEY = "message";

    public static Map<String, Object> buildSuccessResponse(int status, String message, Object data) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(TIMESTAMP_KEY, getCurrentTimestamp());
        response.put(STATUS_KEY, status);
        response.put(MESSAGE_KEY, message);
        response.put("data", data);

        return response;
    }

    public static Map<String, Object> buildErrorResponse(int status, String message) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(TIMESTAMP_KEY, getCurrentTimestamp());
        response.put(STATUS_KEY, status);
        response.put(MESSAGE_KEY, message);

        return response;
    }

    public static Map<String, Object> buildLoginResponse(String token, Long userId, String userName, String userEmail) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(TIMESTAMP_KEY, getCurrentTimestamp());
        response.put(STATUS_KEY, 200);
        response.put(MESSAGE_KEY, "Login successful");

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("token", token);

        Map<String, Object> user = new LinkedHashMap<>();
        user.put("id", userId);
        user.put("name", userName);
        user.put("email", userEmail);

        data.put("user", user);
        response.put("data", data);

        return response;
    }

    private static String getCurrentTimestamp() {
        return LocalDateTime.now().atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ISO_INSTANT);
    }
}