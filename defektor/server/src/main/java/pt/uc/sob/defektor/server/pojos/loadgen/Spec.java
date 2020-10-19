
package pt.uc.sob.defektor.server.pojos.loadgen;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "replicas",
    "selector",
    "template"
})
public class Spec {

    @JsonProperty("replicas")
    private Integer replicas;
    @JsonProperty("selector")
    private Selector selector;
    @JsonProperty("template")
    private Template template;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Spec() {
    }

    /**
     * 
     * @param template
     * @param replicas
     * @param selector
     */
    public Spec(Integer replicas, Selector selector, Template template) {
        super();
        this.replicas = replicas;
        this.selector = selector;
        this.template = template;
    }

    @JsonProperty("replicas")
    public Integer getReplicas() {
        return replicas;
    }

    @JsonProperty("replicas")
    public void setReplicas(Integer replicas) {
        this.replicas = replicas;
    }

    @JsonProperty("selector")
    public Selector getSelector() {
        return selector;
    }

    @JsonProperty("selector")
    public void setSelector(Selector selector) {
        this.selector = selector;
    }

    @JsonProperty("template")
    public Template getTemplate() {
        return template;
    }

    @JsonProperty("template")
    public void setTemplate(Template template) {
        this.template = template;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("replicas", replicas).append("selector", selector).append("template", template).toString();
    }

}
