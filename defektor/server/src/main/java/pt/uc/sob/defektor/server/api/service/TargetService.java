package pt.uc.sob.defektor.server.api.service;

import pt.uc.sob.defektor.server.model.Target;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface TargetService {

    Target targetGet(UUID uuid);

    List<Target> targetsList();
}
