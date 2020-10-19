
package pt.uc.sob.defektor.server.pojos.loadgen;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "matchLabels"
})
public class Selector {

    @JsonProperty("matchLabels")
    private MatchLabels matchLabels;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Selector() {
    }

    /**
     * 
     * @param matchLabels
     */
    public Selector(MatchLabels matchLabels) {
        super();
        this.matchLabels = matchLabels;
    }

    @JsonProperty("matchLabels")
    public MatchLabels getMatchLabels() {
        return matchLabels;
    }

    @JsonProperty("matchLabels")
    public void setMatchLabels(MatchLabels matchLabels) {
        this.matchLabels = matchLabels;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("matchLabels", matchLabels).toString();
    }

}
