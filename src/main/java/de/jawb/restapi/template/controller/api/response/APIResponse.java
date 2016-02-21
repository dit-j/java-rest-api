/**
 * 
 */
package de.jawb.restapi.template.controller.api.response;

import java.util.List;
import java.util.Map;

import de.jawb.restapi.template.model.db.user.User;

/**
 * @author Dieter Kraus (08.10.2015)
 */
public class APIResponse {
    
    private APIResponseStatus     status;
    private APIResponseResultType type;
    private Object                data;      // data sind vom Typ APIResultType
    private long                  time = -1L;
                                       
    APIResponse(APIResponseStatus status, APIResponseResultType type, Object data) {
        super();
        this.status = status;
        this.type = type;
        this.setData(data);
    }
    
    APIResponse(APIResponseStatus status, APIResponseResultType type, Object data, long time) {
        super();
        this.status = status;
        this.type = type;
        this.setData(data);
        this.setTime(time);
    }
    
    public APIResponseStatus getStatus() {
        return status;
    }
    
    public void setStatus(APIResponseStatus status) {
        this.status = status;
    }
    
    public APIResponseResultType getType() {
        return type;
    }
    
    public void setType(APIResponseResultType type) {
        this.type = type;
    }
    
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
    
    public Long getTime() {
        return time;
    }
    
    public void setTime(Long time) {
        this.time = time;
    }
    
    public static Object status(Object status) {
        return new APIResponse(APIResponseStatus.OK, APIResponseResultType.status, status);
    }
    
    public static Object internalError(String message) {
        return new APIResponse(APIResponseStatus.ERROR, APIResponseResultType.error, message);
    }
    
    public static Object errorBadInput(Map<String, List<String>> errors) {
        return new APIResponse(APIResponseStatus.ERROR, APIResponseResultType.errormap, errors);
    }
    
    public static Object user(User user) {
        return new APIResponse(APIResponseStatus.OK, APIResponseResultType.user, user);
    }
    
}
