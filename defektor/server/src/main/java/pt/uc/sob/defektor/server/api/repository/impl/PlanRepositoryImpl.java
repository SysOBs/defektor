//package pt.uc.sob.defektor.server.api.repository.impl;
//
//import org.mapdb.DB;
//import org.mapdb.DBMaker;
//import org.mapdb.Serializer;
//import org.springframework.stereotype.Repository;
//import pt.uc.sob.defektor.server.api.data.PlanData;
//import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
//import pt.uc.sob.defektor.server.api.repository.PlanRepository;
//import pt.uc.sob.defektor.server.utils.Utils;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Repository
//public class PlanRepositoryImpl implements PlanRepository {
//    private final String PLAN_DB_FILE = "state\\plan.db";
//
//    @Override
//    public void save(PlanData plan) throws IOException, DuplicateEntryException {
//
//        DB db = DBMaker.fileDB(PLAN_DB_FILE).make();
//        List<PlanData> planList = (List<PlanData>) db.indexTreeList("planList", Serializer.JAVA).createOrOpen();
//
//        if(Utils.PlanUtils.isDuplicate(planList, plan)) {
//            db.close();
//            //TODO Duplicate Entry message
//            throw new DuplicateEntryException("");
//        }
//
//
//        planList.add(plan);
//
//        db.commit();
//        db.close();
//
//
////        DB db = DBMaker.fileDB(PLAN_DB_FILE).make();
////        List<PlanData> planList = (List<PlanData>) db.indexTreeList("planList", Serializer.JAVA).createOrOpen();
////        planList.add(plan);
////        db.commit();
////        db.close();
////        return plan;
//
////        List<PlanData> planList = Utils.Json.readJsonFromFile(PLAN_DB_FILE, PlanData[].class);
////        if(Utils.PlanUtils.isDuplicate(planList, plan))
////            //TODO Duplicate Entry message
////            throw new DuplicateEntryException("");
////
////        planList.add(plan);
////        Utils.Json.writeJsonToFile(planList, PLAN_DB_FILE);
////        return plan;
//    }
//
//    @Override
//    public PlanData findById(UUID id) throws IOException {
//        DB db = DBMaker.fileDB(PLAN_DB_FILE).make();
//        List<PlanData> planList = (List<PlanData>) db.indexTreeList("planList", Serializer.JAVA).createOrOpen();
//        PlanData returnablePlan = null;
//
//        for(PlanData plan : planList)
//            if(plan.getId().equals(id))
//                returnablePlan = plan;
//
//        db.close();
//
//        return returnablePlan;
////        List<PlanData> planList = Utils.Json.readJsonFromFile(PLAN_DB_FILE, PlanData[].class);
////        for(PlanData plan : planList)
////            if(plan.getId().equals(id))
////                return plan;
////        return null;
//    }
//
//    @Override
//    public void delete(PlanData plan) throws IOException, NullPointerException  {
////        List<PlanData> planList = Utils.Json.readJsonFromFile(PLAN_DB_FILE, PlanData[].class);
////
////        planList.remove(plan);
////        Utils.Json.writeJsonToFile(planList, PLAN_DB_FILE);
//
//        DB db = DBMaker.fileDB(PLAN_DB_FILE).make();
//        List<PlanData> planList = (List<PlanData>) db.indexTreeList("planList", Serializer.JAVA).createOrOpen();
//
//        planList.remove(plan);
//
//        db.close();
//    }
//
//    @Override
//    public List<PlanData> findAll() throws IOException {
//        DB db = DBMaker.fileDB(PLAN_DB_FILE).make();
//        List<PlanData> planList = (List<PlanData>) db.indexTreeList("planList", Serializer.JAVA).createOrOpen();
//        List<PlanData> returnablePlanList = new ArrayList<>(planList);
//
//        db.close();
//
//        return returnablePlanList;
////        DB db = DBMaker.fileDB(PLAN_DB_FILE).make();
////        List<PlanData> planList = (List<PlanData>) db.indexTreeList("planList", Serializer.JAVA).createOrOpen();
////        for (PlanData p: planList) {
////            System.out.println(p.toString());
////        }
////        db.close();
//
//    }
//}
