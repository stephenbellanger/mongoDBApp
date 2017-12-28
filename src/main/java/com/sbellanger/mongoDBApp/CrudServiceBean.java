package com.sbellanger.mongoDBApp;

import com.mongodb.*;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.List;

public class CrudServiceBean {

    private MongoClient mongo;

    public static String DEFAULT_HOST = "localhost";
    public static int PORT = 27017;

    public static String NAME_KEY = "name";
    public static String ID_KEY = "_id";

    public CrudServiceBean(){
        try {
            this.mongo = new MongoClient(DEFAULT_HOST, PORT);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get database to perform all operations on it
     * If database doesn't exists, MongoDB will create it for you
     * @param database : database name
     * @return DB object : the database
     */
    public DB getDB(String database){
        return mongo.getDB(database);
    }

    /**
     * Get table in database
     * if table doesn't exists, MongoDB will create it for you
     * @param database : DB database object
     * @param table : table name
     * @return DBCollection object : table
     */
    public DBCollection getDBTable(DB database, String table){
        return database.getCollection(table);
    }

    /**
     * Get all database
     * @return List<String> database name
     */
    public List getAllDatabase(){
        return  mongo.getDatabaseNames();
    }

    /**
     * Save a document in the table passed as parameter
     * @param table : table name
     * @param document : document to save
     * @return WriteResult : operation result
     */
    public WriteResult save(DBCollection table, BasicDBObject document){
        return table.insert(document);
    }

    /**
     * Delete a document in the table passed as parameter
     * @param table : table name
     * @param id : identifier of document to delete
     * @return WriteResult : operation result
     */
    public WriteResult delete(DBCollection table, String id){
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put(ID_KEY, new ObjectId(id));

        return table.remove(searchQuery);
    }

    /**
     * Update a document in the table passed as parameter
     * @param table : table name
     * @param document : document to update
     * @param id : identifier of document to update
     * @return WriteResult : operation result
     */
    public WriteResult update(DBCollection table, BasicDBObject document, String id){
        BasicDBObject query = new BasicDBObject();
        query.put(ID_KEY, new ObjectId(id));

        return table.update(query, document);
    }

    /**
     * Get a document by his identifier
     * @param table : table name
     * @param id : identifier of document to find
     * @return DBCursor : result cursor
     */
    public DBCursor findById(DBCollection table, String id){
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put(ID_KEY, new ObjectId(id));

        return table.find(searchQuery);
    }

    /**
     * Get a document by his name
     * @param table : table name
     * @param name : name of document to find
     * @return DBCursor : result cursor
     */
    public DBCursor findByName(DBCollection table, String name){
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put(NAME_KEY, name);

        return table.find(searchQuery);
    }

    /**
     * Get all records in table
     * @param table : table name
     * @return DBCursor : result cursor
     */
    public DBCursor getAllRecords(DBCollection table){
        return table.find();
    }

}
