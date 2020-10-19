
package pt.uc.sob.defektor.server.pojos.loadgen;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "labels"
})
public class Metadata {

    @JsonProperty("name")
    private String name;
    @JsonProperty("labels")
    private Labels labels;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Metadata() {
    }

    /**
     * 
     * @param name
     * @param labels
     */
    public Metadata(String name, Labels labels) {
        super();
        this.name = name;
        this.labels = labels;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("labels")
    public Labels getLabels() {
        return labels;
    }

    @JsonProperty("labels")
    public void setLabels(Labels labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("labels", labels).toString();
    }

}
