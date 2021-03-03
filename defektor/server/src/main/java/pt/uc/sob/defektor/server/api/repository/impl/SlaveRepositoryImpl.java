//package pt.uc.sob.defektor.server.api.repository.impl;
//
//import org.springframework.stereotype.Repository;
//import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
//import pt.uc.sob.defektor.server.api.repository.SlaveRepository;
//import pt.uc.sob.defektor.server.api.utils.Utils;
//import pt.uc.sob.defektor.server.model.Slave;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.UUID;
//
//@Repository
//public class SlaveRepositoryImpl implements SlaveRepository {
//    private final String SALVE_DB_FILE = "DefektorState\\slave.json";
//
//    @Override
//    public Slave save(Slave plan) throws IOException, DuplicateEntryException {
//        List<Slave> planList = Utils.Json.readJsonFromFile(SALVE_DB_FILE, Slave[].class);
//        if(Utils.SlaveUtils.isDuplicate(planList, plan))
//            throw new DuplicateEntryException("teste");
//
//        planList.add(plan);
//        Utils.Json.writeJsonToFile(planList, SALVE_DB_FILE);
//        return plan;
//    }
//
//    @Override
//    public Slave findById(UUID id) throws IOException {
//        List<Slave> planList = Utils.Json.readJsonFromFile(SALVE_DB_FILE, Slave[].class);
//        for(Slave plan : planList)
//            if(plan.getId().equals(id))
//                return plan;
//        return null;
//    }
//
//    @Override
//    public void delete(Slave plan) throws IOException,NullPointerException  {
//        List<Slave> planList = Utils.Json.readJsonFromFile(SALVE_DB_FILE, Slave[].class);
//
//        planList.remove(plan);
//        Utils.Json.writeJsonToFile(planList, SALVE_DB_FILE);
//    }
//
//    @Override
//    public List<Slave> findAll() throws IOException {
//        return Utils.Json.readJsonFromFile(SALVE_DB_FILE, Slave[].class);
//    }
//}
