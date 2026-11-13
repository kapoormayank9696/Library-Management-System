package com.library.Library_Management_System.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleIntegrity(DataIntegrityViolationException ex) {
        String root = ex.getMostSpecificCause().getMessage();
        log.warn("Database constraint error: {}", root);
        String m = root == null ? "" : root.toLowerCase();

        String text;
        if (m.contains("cannot delete or update a parent row"))
            text = "Cannot delete: this record is still used by other records (borrow history or books). Delete those first.";
        else if (m.contains("duplicate entry"))
            text = "Duplicate value: a record with this name or email already exists.";
        else if (m.contains("cannot add or update a child row"))
            text = "Invalid reference: the selected author, user or book does not exist.";
        else if (m.contains("cannot be null") || m.contains("doesn't have a default value"))
            text = "A required field is missing.";
        else if (m.contains("check constraint"))
            text = "Invalid value: price and quantity cannot be negative, and the return date cannot be before the borrow date.";
        else
            text = "The database rejected this change.";

        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", text));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException ex) {
        return ResponseEntity.badRequest().body(Map.of("message", String.valueOf(ex.getMessage())));
    }
}