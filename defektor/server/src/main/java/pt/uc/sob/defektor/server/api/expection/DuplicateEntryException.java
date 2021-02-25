package pt.uc.sob.defektor.server.api.expection;

public class DuplicateEntryException extends Exception {

    public DuplicateEntryException(String message){
        super(message);
    }
}
