package pt.uc.sob.defektor.server.api.service;

import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.model.Ijk;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface IjkService {

    List<Ijk> ijkList();

}
