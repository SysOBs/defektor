package pt.uc.sob.defektor.server.api.repository.impl;

import org.mapdb.DB;
import org.mapdb.DBException;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.springframework.stereotype.Repository;
import pt.uc.sob.defektor.server.api.repository.DefektorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Repository
@SuppressWarnings("unchecked")
public class DefektorRepositoryImpl<T> implements DefektorRepository<T> {

    @Override
    public void save(T t, String dbFilePath) {
        DB db = DBMaker.fileDB(dbFilePath).make();
        List<T> tList = (List<T>) db.indexTreeList("list", Serializer.JAVA).createOrOpen();

        tList.add(t);
        db.commit();
        db.close();
    }

    @Override
    public void update(T t, String dbFilePath) {

        DB db = DBMaker.fileDB(dbFilePath).make();
        List<T> tList = (List<T>) db.indexTreeList("list", Serializer.JAVA).createOrOpen();

        for (T tElement : tList) {
            if (tElement.hashCode() == t.hashCode()) {
                int index = tList.indexOf(tElement);
                tList.set(index, t);
            }
        }

        db.commit();
        db.close();
    }

    @Override
    public T findById(UUID id, String dbFilePath) {
        DB db = DBMaker.fileDB(dbFilePath).make();
        List<T> tList = (List<T>) db.indexTreeList("list", Serializer.JAVA).createOrOpen();
        T returnableT = null;

        for (T t : tList)
            if (t.hashCode() == Objects.hash(id))
                returnableT = t;

        db.close();

        return returnableT;
    }

    @Override
    public void delete(T plan, String dbFilePath) {
        DB db = DBMaker.fileDB(dbFilePath).make();
        List<T> tList = (List<T>) db.indexTreeList("list", Serializer.JAVA).createOrOpen();
        tList.remove(plan);
        db.commit();
        db.close();
    }

    @Override
    public void deleteAll(String dbFilePath) {
        DB db = DBMaker.fileDB(dbFilePath).make();
        List<T> tList = (List<T>) db.indexTreeList("list", Serializer.JAVA).createOrOpen();
        tList.clear();
        db.commit();
        db.close();
    }

    @Override
    public List<T> findAll(String dbFilePath) {
        DB db = DBMaker.fileDB(dbFilePath).make();
        List<T> tList = (List<T>) db.indexTreeList("list", Serializer.JAVA).createOrOpen();
        List<T> returnablePlanList = new ArrayList<>(tList);

        db.close();
        return returnablePlanList;
    }
}