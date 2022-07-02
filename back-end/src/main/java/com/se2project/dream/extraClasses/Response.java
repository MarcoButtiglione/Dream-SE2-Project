package com.se2project.dream.extraClasses;


import java.lang.annotation.Annotation;

/**
 * Class Response. This class is used to provide a unique,
 * simple and compact way to provide answer to the WebApp
 * in order to facilitate the functionalities. Each response
 * is made of a code, a message and a result.
 */
public class Response {
    private int code;
    private String message;
    private Iterable<Object> results;

    /**Constructor*/
    public Response(){}

    /**Constructor
     * @param code the code of the Response
     * @param message the message of the Response
     * @param results the result of the Response
     * */

    public Response(int code, String message, Iterable<Object> results){
        this.code=code;
        this.message=message;
        this.results=results;
    }

    /**@return the code of the Response*/
    public int getCode() {
        return code;
    }

    /**Set the code to a Response*/
    public void setCode(int code) {
        this.code = code;
    }

    /**@return the message of the Response*/
    public String getMessage() {
        return message;
    }

    /**Set the message to a Response*/
    public void setMessage(String message) {
        this.message = message;
    }

    /**@return the results of the Response*/
    public  Iterable<Object>  getResults() {
        return results;
    }

    /**Set the results to a Response*/
    public void setResults( Iterable<Object>  results) {
        this.results = results;
    }

}
