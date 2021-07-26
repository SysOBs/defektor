package pt.uc.sob.defektor.server.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import pt.uc.sob.defektor.server.model.KeyValue;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Ijk
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-07-26T23:06:19.537692500+01:00[Europe/Lisbon]")

public class Ijk   {
  @JsonProperty("name")
  private String name;

  @JsonProperty("params")
  @Valid
  private List<KeyValue> params = new ArrayList<>();

  public Ijk name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  @ApiModelProperty(example = "HoleyBoat", required = true, value = "")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Ijk params(List<KeyValue> params) {
    this.params = params;
    return this;
  }

  public Ijk addParamsItem(KeyValue paramsItem) {
    this.params.add(paramsItem);
    return this;
  }

  /**
   * Get params
   * @return params
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<KeyValue> getParams() {
    return params;
  }

  public void setParams(List<KeyValue> params) {
    this.params = params;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ijk ijk = (Ijk) o;
    return Objects.equals(this.name, ijk.name) &&
        Objects.equals(this.params, ijk.params);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, params);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Ijk {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    params: ").append(toIndentedString(params)).append("\n");
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

