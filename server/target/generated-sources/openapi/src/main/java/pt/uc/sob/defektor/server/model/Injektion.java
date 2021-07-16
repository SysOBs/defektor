package pt.uc.sob.defektor.server.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import pt.uc.sob.defektor.server.model.Ijk;
import pt.uc.sob.defektor.server.model.Target;
import pt.uc.sob.defektor.server.model.WorkLoad;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Injektion
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-07-14T01:05:44.936626131+01:00[Europe/Lisbon]")

public class Injektion   {
  @JsonProperty("totalRuns")
  private Integer totalRuns;

  @JsonProperty("ijk")
  private Ijk ijk;

  @JsonProperty("workLoad")
  private WorkLoad workLoad;

  @JsonProperty("target")
  private Target target;

  public Injektion totalRuns(Integer totalRuns) {
    this.totalRuns = totalRuns;
    return this;
  }

  /**
   * Get totalRuns
   * @return totalRuns
  */
  @ApiModelProperty(value = "")


  public Integer getTotalRuns() {
    return totalRuns;
  }

  public void setTotalRuns(Integer totalRuns) {
    this.totalRuns = totalRuns;
  }

  public Injektion ijk(Ijk ijk) {
    this.ijk = ijk;
    return this;
  }

  /**
   * Get ijk
   * @return ijk
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public Ijk getIjk() {
    return ijk;
  }

  public void setIjk(Ijk ijk) {
    this.ijk = ijk;
  }

  public Injektion workLoad(WorkLoad workLoad) {
    this.workLoad = workLoad;
    return this;
  }

  /**
   * Get workLoad
   * @return workLoad
  */
  @ApiModelProperty(value = "")

  @Valid

  public WorkLoad getWorkLoad() {
    return workLoad;
  }

  public void setWorkLoad(WorkLoad workLoad) {
    this.workLoad = workLoad;
  }

  public Injektion target(Target target) {
    this.target = target;
    return this;
  }

  /**
   * Get target
   * @return target
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public Target getTarget() {
    return target;
  }

  public void setTarget(Target target) {
    this.target = target;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Injektion injektion = (Injektion) o;
    return Objects.equals(this.totalRuns, injektion.totalRuns) &&
        Objects.equals(this.ijk, injektion.ijk) &&
        Objects.equals(this.workLoad, injektion.workLoad) &&
        Objects.equals(this.target, injektion.target);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalRuns, ijk, workLoad, target);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Injektion {\n");
    
    sb.append("    totalRuns: ").append(toIndentedString(totalRuns)).append("\n");
    sb.append("    ijk: ").append(toIndentedString(ijk)).append("\n");
    sb.append("    workLoad: ").append(toIndentedString(workLoad)).append("\n");
    sb.append("    target: ").append(toIndentedString(target)).append("\n");
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

