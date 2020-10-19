
package pt.uc.sob.defektor.server.pojos.loadgen;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "env",
    "image",
    "resources"
})
public class Container {

    @JsonProperty("name")
    private String name;
    @JsonProperty("env")
    private List<Env> env = null;
    @JsonProperty("image")
    private String image;
    @JsonProperty("resources")
    private Resources resources;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Container() {
    }

    /**
     * 
     * @param image
     * @param name
     * @param resources
     * @param env
     */
    public Container(String name, List<Env> env, String image, Resources resources) {
        super();
        this.name = name;
        this.env = env;
        this.image = image;
        this.resources = resources;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("env")
    public List<Env> getEnv() {
        return env;
    }

    @JsonProperty("env")
    public void setEnv(List<Env> env) {
        this.env = env;
    }

    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    @JsonProperty("image")
    public void setImage(String image) {
        this.image = image;
    }

    @JsonProperty("resources")
    public Resources getResources() {
        return resources;
    }

    @JsonProperty("resources")
    public void setResources(Resources resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("env", env).append("image", image).append("resources", resources).toString();
    }

}
