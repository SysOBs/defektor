package pt.uc.sob.defektor.server.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkLoadData implements Serializable {
  private DockerImageData image;
  private String cmd;
  private List<String> env;
  private Integer replicas;
  private Integer slaves;
  private Integer duration;
}

