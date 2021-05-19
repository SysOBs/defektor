package pt.uc.sob.defektor.server.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.server.api.data.SlaveData;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.expection.EntityNotFoundException;
import pt.uc.sob.defektor.server.api.mapper.Mapper;
import pt.uc.sob.defektor.server.api.repository.DefektorRepository;
import pt.uc.sob.defektor.server.api.service.SlaveService;
import pt.uc.sob.defektor.server.utils.Strings;
import pt.uc.sob.defektor.server.model.Slave;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SlaveServiceImpl implements SlaveService {

    private final DefektorRepository<SlaveData> defektorRepository;
    private final String slaveDBPath = Strings.DB.SLAVE_DB_PATH;


    @Override
    public void slaveAdd(SlaveData slave) throws DuplicateEntryException {
        defektorRepository.save(slave, slaveDBPath);
    }

    @Override
    public SlaveData slaveGet(UUID id) throws EntityNotFoundException {
        SlaveData slave = defektorRepository.findById(id, slaveDBPath);
        if(slave == null) throw new EntityNotFoundException(Strings.Errors.SLAVE_NOT_FOUND);
        return slave;
    }

    @Override
    public List<SlaveData> slavesList() {
        return defektorRepository.findAll(slaveDBPath);
    }

    @Override
    public void slaveDelete(UUID id) throws EntityNotFoundException {
        SlaveData slave = defektorRepository.findById(id, slaveDBPath);
        if(slave == null) throw new EntityNotFoundException(Strings.Errors.SLAVE_NOT_FOUND);
        defektorRepository.delete(slave, slaveDBPath);
    }
}
