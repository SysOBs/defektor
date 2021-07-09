package pt.uc.sob.defektor.server.api.expection;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(String message){
        super(message);
    }
}
