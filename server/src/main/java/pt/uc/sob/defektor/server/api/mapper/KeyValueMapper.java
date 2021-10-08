package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.KeyValueData;
import pt.uc.sob.defektor.server.model.KeyValue;

public class KeyValueMapper {

    public static KeyValueData convertToDAO(KeyValue key) {
        KeyValueData keyData = new KeyValueData();
        keyData.setKey(key.getKey());
        keyData.setValue(key.getValue());
        return keyData;
    }

    public static KeyValue convertToDTO(KeyValueData keyData) {
        KeyValue key = new KeyValue();
        key.setKey(keyData.getKey());
        key.setValue(keyData.getValue());
        return key;
    }
}
