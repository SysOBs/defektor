
package pt.uc.sob.defektor.server.pojos.loadgen;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "apiVersion",
    "kind",
    "metadata",
    "spec"
})
public class Manifest {

    @JsonProperty("apiVersion")
    private String apiVersion;
    @JsonProperty("kind")
    private String kind;
    @JsonProperty("metadata")
    private Metadata metadata;
    @JsonProperty("spec")
    private Spec spec;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Manifest() {
    }

    /**
     * 
     * @param metadata
     * @param apiVersion
     * @param kind
     * @param spec
     */
    public Manifest(String apiVersion, String kind, Metadata metadata, Spec spec) {
        super();
        this.apiVersion = apiVersion;
        this.kind = kind;
        this.metadata = metadata;
        this.spec = spec;
    }

    @JsonProperty("apiVersion")
    public String getApiVersion() {
        return apiVersion;
    }

    @JsonProperty("apiVersion")
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @JsonProperty("kind")
    public String getKind() {
        return kind;
    }

    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }

    @JsonProperty("metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("spec")
    public Spec getSpec() {
        return spec;
    }

    @JsonProperty("spec")
    public void setSpec(Spec spec) {
        this.spec = spec;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("apiVersion", apiVersion).append("kind", kind).append("metadata", metadata).append("spec", spec).toString();
    }

}
