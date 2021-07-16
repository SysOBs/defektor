package pt.uc.sob.defektor.server.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import pt.uc.sob.defektor.server.model.SSHCredentials;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Slave
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-07-14T01:05:44.936626131+01:00[Europe/Lisbon]")

public class Slave   {
  @JsonProperty("id")
  private UUID id;

  @JsonProperty("address")
  private String address;

  @JsonProperty("port")
  private Integer port = 22;

  @JsonProperty("credentials")
  private SSHCredentials credentials;

  public Slave id(UUID id) {
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

  public Slave address(String address) {
    this.address = address;
    return this;
  }

  /**
   * Get address
   * @return address
  */
  @ApiModelProperty(example = "example.org", required = true, value = "")
  @NotNull


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Slave port(Integer port) {
    this.port = port;
    return this;
  }

  /**
   * Get port
   * @return port
  */
  @ApiModelProperty(example = "22", required = true, value = "")
  @NotNull


  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public Slave credentials(SSHCredentials credentials) {
    this.credentials = credentials;
    return this;
  }

  /**
   * Get credentials
   * @return credentials
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public SSHCredentials getCredentials() {
    return credentials;
  }

  public void setCredentials(SSHCredentials credentials) {
    this.credentials = credentials;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Slave slave = (Slave) o;
    return Objects.equals(this.id, slave.id) &&
        Objects.equals(this.address, slave.address) &&
        Objects.equals(this.port, slave.port) &&
        Objects.equals(this.credentials, slave.credentials);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, address, port, credentials);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Slave {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    port: ").append(toIndentedString(port)).append("\n");
    sb.append("    credentials: ").append(toIndentedString(credentials)).append("\n");
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

