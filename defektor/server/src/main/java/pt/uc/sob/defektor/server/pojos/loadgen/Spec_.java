
package pt.uc.sob.defektor.server.pojos.loadgen;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "containers"
})
public class Spec_ {

    @JsonProperty("containers")
    private List<Container> containers = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Spec_() {
    }

    /**
     * 
     * @param containers
     */
    public Spec_(List<Container> containers) {
        super();
        this.containers = containers;
    }

    @JsonProperty("containers")
    public List<Container> getContainers() {
        return containers;
    }

    @JsonProperty("containers")
    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("containers", containers).toString();
    }

}
