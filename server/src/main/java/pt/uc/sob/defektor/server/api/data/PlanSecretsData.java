package pt.uc.sob.defektor.server.api.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

@Data
@NoArgsConstructor
public class PlanSecretsData implements Serializable {
    private UUID id;
    private Map<String, String> secrets = new HashMap<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanSecretsData otherSecrets = (PlanSecretsData) o;
        return id.equals(otherSecrets.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
