package com.want.base.service.exception;

public class ResponseException extends IllegalStateException {

	private static final long serialVersionUID = 5019643856173941669L;
	private int returnCode;
    private String returnMessage;

    public ResponseException() {
    }

    public ResponseException(int returnCode, String returnMessage) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }
}
