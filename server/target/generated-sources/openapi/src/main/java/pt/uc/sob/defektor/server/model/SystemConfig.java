package pt.uc.sob.defektor.server.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import pt.uc.sob.defektor.server.model.KeyValue;
import pt.uc.sob.defektor.server.model.SystemType;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SystemConfig
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-06-17T12:09:39.110699252+01:00[Europe/Lisbon]")

public class SystemConfig   {
  @JsonProperty("configs")
  @Valid
  private List<KeyValue> configs = null;

  @JsonProperty("systemType")
  private SystemType systemType;

  public SystemConfig configs(List<KeyValue> configs) {
    this.configs = configs;
    return this;
  }

  public SystemConfig addConfigsItem(KeyValue configsItem) {
    if (this.configs == null) {
      this.configs = new ArrayList<>();
    }
    this.configs.add(configsItem);
    return this;
  }

  /**
   * Get configs
   * @return configs
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<KeyValue> getConfigs() {
    return configs;
  }

  public void setConfigs(List<KeyValue> configs) {
    this.configs = configs;
  }

  public SystemConfig systemType(SystemType systemType) {
    this.systemType = systemType;
    return this;
  }

  /**
   * Get systemType
   * @return systemType
  */
  @ApiModelProperty(value = "")

  @Valid

  public SystemType getSystemType() {
    return systemType;
  }

  public void setSystemType(SystemType systemType) {
    this.systemType = systemType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SystemConfig systemConfig = (SystemConfig) o;
    return Objects.equals(this.configs, systemConfig.configs) &&
        Objects.equals(this.systemType, systemConfig.systemType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(configs, systemType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SystemConfig {\n");
    
    sb.append("    configs: ").append(toIndentedString(configs)).append("\n");
    sb.append("    systemType: ").append(toIndentedString(systemType)).append("\n");
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

