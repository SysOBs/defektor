package pt.uc.sob.defektor.server.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import pt.uc.sob.defektor.server.model.Injektion;
import pt.uc.sob.defektor.server.model.SystemType;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Plan
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-09-14T17:54:31.910834+01:00[Europe/Lisbon]")
public class Plan   {
  @JsonProperty("id")
  private UUID id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("system")
  private SystemType system;

  @JsonProperty("injektions")
  @Valid
  private List<Injektion> injektions = new ArrayList<>();

  public Plan id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(example = "d290f1ee-6c54-4b01-90e6-d701748f0851", value = "")

  @Valid

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Plan name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  @ApiModelProperty(example = "Order 66", required = true, value = "")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Plan system(SystemType system) {
    this.system = system;
    return this;
  }

  /**
   * Get system
   * @return system
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public SystemType getSystem() {
    return system;
  }

  public void setSystem(SystemType system) {
    this.system = system;
  }

  public Plan injektions(List<Injektion> injektions) {
    this.injektions = injektions;
    return this;
  }

  public Plan addInjektionsItem(Injektion injektionsItem) {
    this.injektions.add(injektionsItem);
    return this;
  }

  /**
   * Get injektions
   * @return injektions
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<Injektion> getInjektions() {
    return injektions;
  }

  public void setInjektions(List<Injektion> injektions) {
    this.injektions = injektions;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Plan plan = (Plan) o;
    return Objects.equals(this.id, plan.id) &&
        Objects.equals(this.name, plan.name) &&
        Objects.equals(this.system, plan.system) &&
        Objects.equals(this.injektions, plan.injektions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, system, injektions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Plan {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    system: ").append(toIndentedString(system)).append("\n");
    sb.append("    injektions: ").append(toIndentedString(injektions)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

