package com.hourtimesheet.model;

/**
 * Created by Noor's on 4/26/2017.
 */
public class FileResponseModel {

    private String fileText;
    private String errorMessage;
    private boolean errorIndicator;

    public FileResponseModel() {
    }

    public FileResponseModel(String fileText, String errorMessage, boolean errorIndicator) {
        this.fileText = fileText;
        this.errorMessage = errorMessage;
        this.errorIndicator = errorIndicator;
    }

    public String getFileText() {
        return fileText;
    }

    public void setFileText(String fileText) {
        this.fileText = fileText;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isErrorIndicator() {
        return errorIndicator;
    }

    public void setErrorIndicator(boolean errorIndicator) {
        this.errorIndicator = errorIndicator;
    }
}
