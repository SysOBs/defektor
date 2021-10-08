package pt.uc.sob.defektor.server.api.data;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataOutputURIData implements Serializable {
    private String goldenRunURI;
    private String faultInjectionURI;

    public DataOutputURIData() {
        this.goldenRunURI = "";
        this.faultInjectionURI = "";
    }
}
