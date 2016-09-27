package org.omapper.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxwang on 16/9/27.
 */
public class NecessaryFieldEmptyException extends RuntimeException {
    private List<String> mNecessaryField=new ArrayList<>();

    public void addField(String string){
        mNecessaryField.add(string);
    }

    public NecessaryFieldEmptyException(){
        super();
    }

    public NecessaryFieldEmptyException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new unknown property exception.
     *
     * @param message the message
     */
    public NecessaryFieldEmptyException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new unknown property exception.
     *
     * @param cause the cause
     */
    public NecessaryFieldEmptyException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
}
