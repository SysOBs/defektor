
package pt.uc.sob.defektor.server.pojos.loadgen;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "limits",
    "requests"
})
public class Resources {

    @JsonProperty("limits")
    private Limits limits;
    @JsonProperty("requests")
    private Requests requests;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Resources() {
    }

    /**
     * 
     * @param requests
     * @param limits
     */
    public Resources(Limits limits, Requests requests) {
        super();
        this.limits = limits;
        this.requests = requests;
    }

    @JsonProperty("limits")
    public Limits getLimits() {
        return limits;
    }

    @JsonProperty("limits")
    public void setLimits(Limits limits) {
        this.limits = limits;
    }

    @JsonProperty("requests")
    public Requests getRequests() {
        return requests;
    }

    @JsonProperty("requests")
    public void setRequests(Requests requests) {
        this.requests = requests;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("limits", limits).append("requests", requests).toString();
    }

}
