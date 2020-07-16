package pt.uc.sob.defektor.server.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@Controller
@RequestMapping("${openapi.server.base-path:/defektor-api/1.0.0}")
public class TargetController implements TargetApi {

    private final NativeWebRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public TargetController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }
}
