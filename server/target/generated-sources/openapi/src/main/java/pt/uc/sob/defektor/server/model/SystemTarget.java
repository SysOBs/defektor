package pt.uc.sob.defektor.server.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import pt.uc.sob.defektor.server.model.TargetType;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SystemTarget
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-06-17T12:09:39.110699252+01:00[Europe/Lisbon]")

public class SystemTarget   {
  @JsonProperty("name")
  private String name;

  @JsonProperty("targetTypes")
  @Valid
  private List<TargetType> targetTypes = null;

  public SystemTarget name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  @ApiModelProperty(example = "kubernetes", required = true, value = "")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public SystemTarget targetTypes(List<TargetType> targetTypes) {
    this.targetTypes = targetTypes;
    return this;
  }

  public SystemTarget addTargetTypesItem(TargetType targetTypesItem) {
    if (this.targetTypes == null) {
      this.targetTypes = new ArrayList<>();
    }
    this.targetTypes.add(targetTypesItem);
    return this;
  }

  /**
   * Get targetTypes
   * @return targetTypes
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<TargetType> getTargetTypes() {
    return targetTypes;
  }

  public void setTargetTypes(List<TargetType> targetTypes) {
    this.targetTypes = targetTypes;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SystemTarget systemTarget = (SystemTarget) o;
    return Objects.equals(this.name, systemTarget.name) &&
        Objects.equals(this.targetTypes, systemTarget.targetTypes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, targetTypes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SystemTarget {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    targetTypes: ").append(toIndentedString(targetTypes)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

