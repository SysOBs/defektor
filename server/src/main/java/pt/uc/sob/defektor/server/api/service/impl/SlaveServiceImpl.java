package pt.uc.sob.defektor.server.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.server.api.data.SlaveData;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.expection.EntityNotFoundException;
import pt.uc.sob.defektor.server.api.repository.DefektorRepository;
import pt.uc.sob.defektor.server.api.service.SlaveService;
import pt.uc.sob.defektor.server.utils.Strings;
import pt.uc.sob.defektor.server.utils.Utils;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SlaveServiceImpl implements SlaveService {

    private final DefektorRepository<SlaveData> defektorRepository;

    @Override
    public SlaveData slaveAdd(SlaveData slave) throws DuplicateEntryException {
        slave.setId(Utils.generateUUID());
        defektorRepository.save(slave, Strings.DB.SLAVE_DB_PATH);
        return slave;
    }

    @Override
    public SlaveData slaveGet(UUID id) throws EntityNotFoundException {
        SlaveData slave = defektorRepository.findById(id, Strings.DB.SLAVE_DB_PATH);
        if (slave == null) throw new EntityNotFoundException(Strings.Errors.SLAVE_NOT_FOUND);
        return slave;
    }

    @Override
    public List<SlaveData> slavesList() {
        return defektorRepository.findAll(Strings.DB.SLAVE_DB_PATH);
    }

    @Override
    public void slaveDelete(UUID id) throws EntityNotFoundException {
        SlaveData slave = defektorRepository.findById(id, Strings.DB.SLAVE_DB_PATH);
        if (slave == null) throw new EntityNotFoundException(Strings.Errors.SLAVE_NOT_FOUND);
        defektorRepository.delete(slave, Strings.DB.SLAVE_DB_PATH);
    }

    @Override
    public void slaveDeleteAll() {
        defektorRepository.deleteAll(Strings.DB.SLAVE_DB_PATH);
    }
}
