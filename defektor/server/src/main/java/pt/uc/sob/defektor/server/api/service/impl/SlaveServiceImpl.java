package pt.uc.sob.defektor.server.api.service.impl;

import com.sun.xml.bind.v2.TODO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.uc.sob.defektor.server.api.data.SlaveData;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.expection.EntityNotFoundException;
import pt.uc.sob.defektor.server.api.mapper.Mapper;
import pt.uc.sob.defektor.server.api.repository.DefektorRepository;
import pt.uc.sob.defektor.server.api.service.SlaveService;
import pt.uc.sob.defektor.server.api.utils.Utils;
import pt.uc.sob.defektor.server.model.Slave;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SlaveServiceImpl implements SlaveService {

    private final DefektorRepository defektorRepository;
    private final String slaveDbFileDir = Utils.getSlaveFileDB();


    @Override
    public Slave slaveAdd(Slave slave) throws DuplicateEntryException {
        defektorRepository.save(Mapper.convertToDAO(slave), slaveDbFileDir);
        return slave;
    }

    @Override
    public Slave slaveGet(UUID id) throws EntityNotFoundException {
        SlaveData slave = (SlaveData) defektorRepository.findById(id, slaveDbFileDir);

        if(slave == null)
            //TODO
            throw new EntityNotFoundException("");

        return Mapper.convertToDTO(slave);
    }

    @Override
    public List<Slave> slavesList() {
        return (List<Slave>) defektorRepository.findAll(slaveDbFileDir).stream()
                .map(Mapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void slaveDelete(UUID id) throws EntityNotFoundException {
        SlaveData slave = (SlaveData) defektorRepository.findById(id, slaveDbFileDir);
        if(slave == null) {
            //TODO Message
            throw new EntityNotFoundException("Test");
        }
        defektorRepository.delete(slave, slaveDbFileDir);
    }
}