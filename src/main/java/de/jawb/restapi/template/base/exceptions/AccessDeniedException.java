package de.jawb.restapi.template.base.exceptions;

public class AccessDeniedException extends BaseAppException {

    private static final long serialVersionUID = 1L;

    public AccessDeniedException() {
        super("403 - access denied");
    }
}
