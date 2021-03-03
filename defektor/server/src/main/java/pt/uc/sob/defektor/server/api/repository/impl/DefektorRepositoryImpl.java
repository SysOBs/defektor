package pt.uc.sob.defektor.server.api.repository.impl;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.springframework.stereotype.Repository;
import pt.uc.sob.defektor.server.api.expection.DuplicateEntryException;
import pt.uc.sob.defektor.server.api.repository.DefektorRepository;
import pt.uc.sob.defektor.server.api.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Repository
public class DefektorRepositoryImpl<T> implements DefektorRepository<T> {

    @Override
    public void save(T t, String dbFileDir) throws DuplicateEntryException {
        DB db = DBMaker.fileDB(dbFileDir).make();
        List<T> tList = (List<T>) db.indexTreeList("list", Serializer.JAVA).createOrOpen();

        if(Utils.isUnique(t, tList) == false) {
            db.close();
            throw new DuplicateEntryException("Test");
        }

        tList.add(t);

        db.commit();
        db.close();
    }

    @Override
    public T findById(UUID id, String dbFileDir) {
        DB db = DBMaker.fileDB(dbFileDir).make();
        List<T> tList = (List<T>) db.indexTreeList("list", Serializer.JAVA).createOrOpen();
        T returnableT = null;

        for(T t : tList)
            if (t.hashCode() == Objects.hash(id))
                returnableT = t;

        db.close();

        return returnableT;
    }

    @Override
    public void delete(T plan, String dbFileDir) throws NullPointerException {
        DB db = DBMaker.fileDB(dbFileDir).make();
        List<T> tList = (List<T>) db.indexTreeList("list", Serializer.JAVA).createOrOpen();

        tList.remove(plan);

        db.close();
    }

    @Override
    public List<T> findAll(String dbFileDir) {
        DB db = DBMaker.fileDB(dbFileDir).make();
        List<T> tList = (List<T>) db.indexTreeList("list", Serializer.JAVA).createOrOpen();
        List<T> returnablePlanList = new ArrayList<>(tList);

        db.close();
        return returnablePlanList;
    }
}