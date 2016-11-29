/**
 *
 */
package de.jawb.restapi.template.base.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

import de.jawb.restapi.template.controller.api.response.APIResponse;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BaseAppException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BaseAppException() {
        super();
//        MysqlConnectionPoolDataSource d = new MysqlConnectionPoolDataSource();
//        System.out.println(d);
    }

    public BaseAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseAppException(String message) {
        super(message);
    }

    public BaseAppException(Throwable cause) {
        super(cause);
    }

    public Object createJsonResponse() {
        return APIResponse.internalError(getMessage());
    }
}
