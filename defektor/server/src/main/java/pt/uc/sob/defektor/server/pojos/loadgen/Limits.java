
package pt.uc.sob.defektor.server.pojos.loadgen;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "cpu",
    "memory"
})
public class Limits {

    @JsonProperty("cpu")
    private String cpu;
    @JsonProperty("memory")
    private String memory;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Limits() {
    }

    /**
     * 
     * @param memory
     * @param cpu
     */
    public Limits(String cpu, String memory) {
        super();
        this.cpu = cpu;
        this.memory = memory;
    }

    @JsonProperty("cpu")
    public String getCpu() {
        return cpu;
    }

    @JsonProperty("cpu")
    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    @JsonProperty("memory")
    public String getMemory() {
        return memory;
    }

    @JsonProperty("memory")
    public void setMemory(String memory) {
        this.memory = memory;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("cpu", cpu).append("memory", memory).toString();
    }

}
