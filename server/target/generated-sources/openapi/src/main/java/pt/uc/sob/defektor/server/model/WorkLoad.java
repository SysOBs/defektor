package pt.uc.sob.defektor.server.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import pt.uc.sob.defektor.server.model.DockerImage;
import pt.uc.sob.defektor.server.model.KeyValue;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * WorkLoad
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-07-26T23:06:19.537692500+01:00[Europe/Lisbon]")

public class WorkLoad   {
  @JsonProperty("image")
  private DockerImage image;

  @JsonProperty("cmd")
  private String cmd;

  @JsonProperty("env")
  @Valid
  private List<KeyValue> env = null;

  @JsonProperty("replicas")
  private Integer replicas = 1;

  @JsonProperty("slaves")
  private Integer slaves = 1;

  @JsonProperty("duration")
  private Integer duration = 120;

  public WorkLoad image(DockerImage image) {
    this.image = image;
    return this;
  }

  /**
   * Get image
   * @return image
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public DockerImage getImage() {
    return image;
  }

  public void setImage(DockerImage image) {
    this.image = image;
  }

  public WorkLoad cmd(String cmd) {
    this.cmd = cmd;
    return this;
  }

  /**
   * Get cmd
   * @return cmd
  */
  @ApiModelProperty(example = "sh shesellsshellsbytheseashore.sh", value = "")


  public String getCmd() {
    return cmd;
  }

  public void setCmd(String cmd) {
    this.cmd = cmd;
  }

  public WorkLoad env(List<KeyValue> env) {
    this.env = env;
    return this;
  }

  public WorkLoad addEnvItem(KeyValue envItem) {
    if (this.env == null) {
      this.env = new ArrayList<>();
    }
    this.env.add(envItem);
    return this;
  }

  /**
   * Get env
   * @return env
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<KeyValue> getEnv() {
    return env;
  }

  public void setEnv(List<KeyValue> env) {
    this.env = env;
  }

  public WorkLoad replicas(Integer replicas) {
    this.replicas = replicas;
    return this;
  }

  /**
   * Get replicas
   * @return replicas
  */
  @ApiModelProperty(example = "1", value = "")


  public Integer getReplicas() {
    return replicas;
  }

  public void setReplicas(Integer replicas) {
    this.replicas = replicas;
  }

  public WorkLoad slaves(Integer slaves) {
    this.slaves = slaves;
    return this;
  }

  /**
   * Get slaves
   * @return slaves
  */
  @ApiModelProperty(example = "1", value = "")


  public Integer getSlaves() {
    return slaves;
  }

  public void setSlaves(Integer slaves) {
    this.slaves = slaves;
  }

  public WorkLoad duration(Integer duration) {
    this.duration = duration;
    return this;
  }

  /**
   * Duration of the workload in seconds. If the container terminates earlier it gets restarted.
   * @return duration
  */
  @ApiModelProperty(example = "120", value = "Duration of the workload in seconds. If the container terminates earlier it gets restarted.")


  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorkLoad workLoad = (WorkLoad) o;
    return Objects.equals(this.image, workLoad.image) &&
        Objects.equals(this.cmd, workLoad.cmd) &&
        Objects.equals(this.env, workLoad.env) &&
        Objects.equals(this.replicas, workLoad.replicas) &&
        Objects.equals(this.slaves, workLoad.slaves) &&
        Objects.equals(this.duration, workLoad.duration);
  }

  @Override
  public int hashCode() {
    return Objects.hash(image, cmd, env, replicas, slaves, duration);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WorkLoad {\n");
    
    sb.append("    image: ").append(toIndentedString(image)).append("\n");
    sb.append("    cmd: ").append(toIndentedString(cmd)).append("\n");
    sb.append("    env: ").append(toIndentedString(env)).append("\n");
    sb.append("    replicas: ").append(toIndentedString(replicas)).append("\n");
    sb.append("    slaves: ").append(toIndentedString(slaves)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
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

