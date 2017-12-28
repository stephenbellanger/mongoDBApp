package com.sbellanger.mongoDBApp;

import com.mongodb.*;

import java.util.Date;
import java.util.List;

public class App {

    public static void main(String[] args) {
        CrudServiceBean crudServiceBean = new CrudServiceBean();
        List<String> databases = crudServiceBean.getAllDatabase();

        //Show all databases
        databases.forEach(s -> System.out.println(s));

        //DB object allows all operations on the current database
        DB db = crudServiceBean.getDB("database");

        DBCollection table = crudServiceBean.getDBTable(db, "users");

        BasicDBObject  document = new BasicDBObject();
        document.put("name", "Paul");
        document.put("age", 39);
        document.put("createdDate", new Date());

        //Save document in database name : "database"
        //crudServiceBean.save(table, document);

        //Delete document with objectid of function parameters
        //WriteResult deleteResult = crudServiceBean.delete(table, "5a44f1d63a5c4ceebec3bbb5");

        //Show all records by name
        DBCursor result = crudServiceBean.findByName(table, "Paul");
        System.out.println("Records with Paul name...");
        result.forEach(r -> System.out.println(r));

        //Get all records in table
        DBCursor result2 = crudServiceBean.getAllRecords(table);
        System.out.println("All records...");
        result2.forEach(r -> System.out.println(r));

        BasicDBObject  updateDocument = new BasicDBObject();
        updateDocument.put("name", "Robert");
        updateDocument.put("age", 99999);
        updateDocument.put("createdDate", new Date());

        //Update record by id
        crudServiceBean.update(table, updateDocument,"5a44f26d3a5c6c63bf401871");

        DBCursor updateResult = crudServiceBean.findByName(table, "Robert");
        System.out.println("Update verification...");
        updateResult.forEach(r -> System.out.println(r));

        //Example how to use DBObject
        DBCursor cursor = crudServiceBean.findById(table, "5a44f26d3a5c6c63bf401871");
        List<DBObject> objects = cursor.toArray();
        DBObject dbObject = objects.get(0);

        System.out.println("Show all parameters in document...");
        System.out.println("name : " + dbObject.get("name"));
        System.out.println("age : " + dbObject.get("age"));
        System.out.println("date : " + dbObject.get("createdDate"));
    }

}
