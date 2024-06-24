package ru.netology.cloudstorage.exceptions;


public class MessageConstant {

    // Error constants
    public final static String LOGIN_NON_VALID_VALUE = "Login or password cannot be empty!";
    public final static String LOGIN_NOT_FOUND_USER = "A user with this username was not found!";
    public final static String LOGIN_NOT_VALID_PASSWORD = "The password is not correct!";
    public final static String ERROR_UPLOAD_NOT_UNIQUE_FILE = "Error upload file! Filename is not uniq";
    public final static String ERROR_UPLOAD_FILE = "Error upload file!";
    public final static String ERROR_DELETE_FILENAME = "Error delete file!";
    public final static String ERROR_RENAME_FILENAME = "File is not exist!";

    // Message constants
    public final static String SUCCESS_UPLOAD = "Success upload!";
    public final static String SUCCESS_DELETE = "Success deleted!";
    public final static String SUCCESS_RENAME = "Success rename!";



    //  Path constant
    public final static String FILE = "/file";
    public final static String LIST = "/list";
    public final static String LOGIN= "/login";

}
