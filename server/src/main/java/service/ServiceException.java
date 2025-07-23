package service;

public class ServiceException extends Exception {
    private final int errorCode;
    private final String message;
    public ServiceException(int errorCode) {
        this(errorCode, "");
    }
    public ServiceException(int errorCode, String error) {
        String message = "";
        switch(errorCode) {
            case 400:
                message = "{ \"message\": \"Error: bad request\" }";
                break;
            case 401:
                message = "{ \"message\": \"Error: unauthorized\" }";
                break;
            case 403:
                message = "{ \"message\": \"Error: already taken\" }";
                break;
            case 500:
                message = "{ \"message\": \"Error: " +error+ "\" }";
            default:
                message = "{ \"message\": \"Error: unknown\" }";
                break;
        }
        //super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
    //public ServiceException(String message, Throwable ex) { super(message, ex); }
}
