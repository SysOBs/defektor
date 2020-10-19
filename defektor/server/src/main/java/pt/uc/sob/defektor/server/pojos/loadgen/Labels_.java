
package pt.uc.sob.defektor.server.pojos.loadgen;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "service"
})
public class Labels_ {

    @JsonProperty("service")
    private String service;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Labels_() {
    }

    /**
     * 
     * @param service
     */
    public Labels_(String service) {
        super();
        this.service = service;
    }

    @JsonProperty("service")
    public String getService() {
        return service;
    }

    @JsonProperty("service")
    public void setService(String service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("service", service).toString();
    }

}
