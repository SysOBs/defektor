package pt.uc.sob.defektor.server.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlaveData implements Serializable {
  private UUID id;
  private String address;
  private Integer port;
  private SSHCredentialsData credentials;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SlaveData slaveData = (SlaveData) o;
    return id.equals(slaveData.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}

