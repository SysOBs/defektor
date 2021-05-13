package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.KeyData;
import pt.uc.sob.defektor.server.api.data.SSHCredentialsData;
import pt.uc.sob.defektor.server.model.Key;
import pt.uc.sob.defektor.server.model.SSHCredentials;

public class KeyMapper {

    public static KeyData convertToDAO(Key key) {
        KeyData keyData = new KeyData();
        keyData.setName(key.getName());
        keyData.setValue(key.getValue());
        return keyData;
    }


    public static Key convertToDTO(KeyData keyData) {
        Key key = new Key();
        key.setName(keyData.getName());
        key.setValue(keyData.getValue());
        return key;
    }


}
