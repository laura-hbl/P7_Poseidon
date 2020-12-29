package com.nnk.springboot.exception;

import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

/**
 * Contains methods that handles exceptions across the whole application.
 *
 * @author Laura Habdul
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * GlobalExceptionHandler logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles exception of the specific type DataAlreadyRegisteredException.
     *
     * @param ex      DataAlreadyRegisteredException object
     * @param response HttpServletResponse object
     */
    @ExceptionHandler(DataAlreadyRegisteredException.class)
    public void handleConflict(final DataAlreadyRegisteredException ex, final HttpServletResponse response)
            throws IOException {
        LOGGER.error("Request - FAILED :", ex);

        response.setStatus(HttpStatus.CONFLICT.value());
        response.sendRedirect("/user/add?error");
    }
}
