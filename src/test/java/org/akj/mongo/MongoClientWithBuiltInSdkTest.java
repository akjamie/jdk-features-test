package org.akj.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MongoClientWithBuiltInSdkTest {
    private MongoClient client;

    private static final String DB_URL = "mongodb://localhost:27017";

    private static final String DATABASE_NAME = "demo";

    private static final String COLLECTION = "users";

    private MongoCollection collection;

    @BeforeEach
    public void setup() {
        MongoClientURI clientURI = new MongoClientURI(DB_URL);
        client = new MongoClient(clientURI);

        MongoDatabase database = client.getDatabase(DATABASE_NAME);

        collection = database.getCollection(COLLECTION);

        // set unique index for userName
//        IndexOptions indexOptions = new IndexOptions();
//        indexOptions.unique(true);
//        indexOptions.name("users_uni_username");
//        collection.createIndex(new Document("userName", 1), indexOptions);
    }

    @Test
    public void testInsert() {
        String id = UUID.randomUUID().toString();
        Document userInfo = new Document().append("tags", Arrays.asList("Springboot", "Mongo"));
        Document document = new Document().append("_id", id)
                .append("userName", "Test-" + id.replace("-", "").substring(0, 8))
                .append("authType", "WECHAT").append("createDate", new Date()).append("schemaVersion", "1.0.0").append("userInfo", userInfo);

        collection.insertOne(document);


        //verify the insertion
        FindIterable iterable = collection.find(new BasicDBObject().append("_id", id));
        Document first = (Document) iterable.first();

        assertNotNull(first);
        assertEquals(id, first.getString("_id"));
    }

    @Test
    public void testUpdate() {
        // insert one document
        String id = UUID.randomUUID().toString();
        Document userInfo = new Document().append("tags", Arrays.asList("Springboot", "Mongo"));
        Document document = new Document().append("_id", id)
                .append("userName", "Test-" + id.replace("-", "").substring(0, 8))
                .append("authType", "WECHAT").append("createDate", new Date()).append("schemaVersion", "1.0.0").append("userInfo", userInfo);
        collection.insertOne(document);


        // try to update it
        //Document update = new Document().append("tags", Arrays.asList("Mysql","Scala"));
        collection.updateOne(document, new Document("$addToSet", new Document("userInfo.tags", new Document("$each", Arrays.asList("Mysql", "Scala")))));

        // find the document and check the update
        FindIterable iterable = collection.find(new BasicDBObject().append("_id", id));
        Document first = (Document) iterable.first();

        assertNotNull(first);
        assertEquals(4, ((List<String>) (((Document) first.get("userInfo")).get("tags"))).size());
    }

}
