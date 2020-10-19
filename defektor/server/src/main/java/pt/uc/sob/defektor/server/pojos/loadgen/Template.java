
package pt.uc.sob.defektor.server.pojos.loadgen;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "metadata",
    "spec"
})
public class Template {

    @JsonProperty("metadata")
    private Metadata_ metadata;
    @JsonProperty("spec")
    private Spec_ spec;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Template() {
    }

    /**
     * 
     * @param metadata
     * @param spec
     */
    public Template(Metadata_ metadata, Spec_ spec) {
        super();
        this.metadata = metadata;
        this.spec = spec;
    }

    @JsonProperty("metadata")
    public Metadata_ getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(Metadata_ metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("spec")
    public Spec_ getSpec() {
        return spec;
    }

    @JsonProperty("spec")
    public void setSpec(Spec_ spec) {
        this.spec = spec;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("metadata", metadata).append("spec", spec).toString();
    }

}
