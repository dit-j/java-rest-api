package de.jawb.restapi.template.controller.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import de.jawb.restapi.template.base.exceptions.BaseAppException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseAppException.class)
    public Object handleException(HttpServletRequest request, HttpServletResponse response, BaseAppException ex) {
        logger.error("handle BaseAppException: {}", ex.getMessage());
        return ex.createJsonResponse();
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public Object handleException(HttpServletRequest request, Exception ex) {
        ex.printStackTrace();
        logger.error("handle Exception: {}", ex.getMessage());
        return new BaseAppException(ex).createJsonResponse();
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Object handleException(HttpServletRequest request, NoHandlerFoundException ex) {
        logger.error("handle NoHandlerFoundException: {}", ex.getMessage());
        return new BaseAppException("'" + request.getRequestURI() + "' not found").createJsonResponse();
    }

}
