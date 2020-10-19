
package pt.uc.sob.defektor.server.pojos.loadgen;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "labels"
})
public class Metadata_ {

    @JsonProperty("labels")
    private Labels_ labels;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Metadata_() {
    }

    /**
     * 
     * @param labels
     */
    public Metadata_(Labels_ labels) {
        super();
        this.labels = labels;
    }

    @JsonProperty("labels")
    public Labels_ getLabels() {
        return labels;
    }

    @JsonProperty("labels")
    public void setLabels(Labels_ labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("labels", labels).toString();
    }

}
