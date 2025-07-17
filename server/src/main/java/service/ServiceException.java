package service;

public class ServiceException extends Exception {
    private final int errorCode;
    private final String message;
    public ServiceException(int errorCode) {
        String message = "";
        switch(errorCode) {
            case 400:
                message = "{ \"message\": \"Error: bad request\" }";
                break;
            case 401:
                message = "{ \"message\": \"Error: unauthorized\" }";
            case 403:
                message = "{ \"message\": \"Error: already taken\" }";
                break;
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
    //public ServiceException(String message, Throwable ex) { super(message, ex); }
}
