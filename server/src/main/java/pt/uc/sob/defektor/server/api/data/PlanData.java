package pt.uc.sob.defektor.server.api.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PlanData implements Serializable {
  private UUID id;
  private String name;
  private SystemTypeData system;
  private List<IjkData> injektors = new ArrayList<>();
  private List<InjektionData> injektions = new ArrayList<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PlanData planData = (PlanData) o;
    return id.equals(planData.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}

