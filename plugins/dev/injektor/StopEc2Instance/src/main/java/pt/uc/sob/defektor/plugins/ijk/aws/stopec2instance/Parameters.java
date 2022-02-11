package pt.uc.sob.defektor.plugins.ijk.aws.stopec2instance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class Parameters {
    private String experimentTemplateId;
    private Map<String, String> tags;
}
