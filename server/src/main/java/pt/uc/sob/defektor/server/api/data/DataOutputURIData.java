package pt.uc.sob.defektor.server.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
