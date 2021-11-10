package Exceptions;

public class NoItemInventoryException extends Exception{

    @Override
    public void printStackTrace(){
        System.out.println(getMessage());
    }

    @Override
    public String getMessage(){
        return "Stack Trace: Error = This item is out of stock";
    }
}
