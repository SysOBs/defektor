package pt.uc.sob.defektor.server.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.repository.SlaveRepository;
import pt.uc.sob.defektor.server.api.service.SlaveService;
import pt.uc.sob.defektor.server.model.Slave;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SlaveServiceImpl implements SlaveService {

    private final SlaveRepository slaveRepository;

    @Override
    public Slave slaveAdd(Slave slave) throws IOException, DuplicateEntryException {
        return slaveRepository.save(slave);
    }

    @Override
    public Slave slaveGet(UUID id) throws IOException {
        return slaveRepository.findById(id);
    }

    @Override
    public List<Slave> slavesList() throws IOException {
        return slaveRepository.findAll();
    }

    @Override
    public void slaveDelete(UUID id) throws IOException, NullPointerException {
        Slave slave = slaveRepository.findById(id);
        if(slave == null) {
            throw new NullPointerException();
        }
        slaveRepository.delete(slave);
    }
}
