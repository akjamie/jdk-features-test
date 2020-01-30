package org.akj.mongo;


import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.IndexOptions;
import lombok.extern.slf4j.Slf4j;
import org.akj.mongo.bean.GenderEnum;
import org.akj.mongo.bean.User;
import org.akj.mongo.bean.UserInfo;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.json.JsonWriterSettings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Consumer;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Slf4j
public class MongoClientTest {

    private MongoClient client;

//    private static final String url = "mongodb://localhost:28017,localhost:28018," +
//            "localhost:28019/test?replicaSet=mongo-rs&slaveOk=true&readPreference=secondaryPreferred";

    private static final String url = "mongodb://localhost:27017";

    private static final String DATABASE = "test";

    private static final String COLLECTION = "users";

    private MongoCollection collection;

    @BeforeEach
    public void setup() {
        MongoClientURI clientURI = new MongoClientURI(url);
        client = new MongoClient(clientURI);

        PojoCodecProvider pojoCodecProvider =
                PojoCodecProvider.builder().register("org.akj.mongo.bean").conventions(Arrays.asList(Conventions.ANNOTATION_CONVENTION, Conventions.OBJECT_ID_GENERATORS, Conventions.CLASS_AND_PROPERTY_CONVENTION)).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(pojoCodecProvider));

        MongoDatabase database = client.getDatabase(MongoClientTest.DATABASE);

        boolean collectionExists = client.getDatabase(DATABASE).listCollectionNames()
                .into(new ArrayList<String>()).contains(COLLECTION);
        if (!collectionExists) {
            database.createCollection("users");
            collection =
                    database.getCollection("users", User.class).withCodecRegistry(pojoCodecRegistry);
            IndexOptions indexOptions = new IndexOptions();
            indexOptions.unique(true);
            indexOptions.name("users_uni_username");

            collection.createIndex(new Document("userName", 1), indexOptions);
        }

        collection =
                database.getCollection(MongoClientTest.COLLECTION).withCodecRegistry(pojoCodecRegistry);

    }

    @Test
    public void testConnection() {
        Assertions.assertNotNull(client, "mongo db connection issue");
        MongoIterable<String> actual = client.listDatabaseNames();
        Assertions.assertNotNull(actual);

        log.info("{}", actual);
        actual.forEach((Consumer<? super String>) s -> log.info(s));
    }

    @Test
    public void testInsert() {
        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName("Jamie");
        userInfo.setLastName("Zhang");
        userInfo.setAge(31);
        userInfo.setGender(GenderEnum.MALE);
        userInfo.setPhone("13991999999");

        User u = new User();
        u.setUserInfo(userInfo);
        u.setAuthType("PASSWORD");
        u.setUserName("jamie-002");
        u.setPassword("123456");
        Date date = new Date();
        u.setCreateDate(date);
        u.setLastUpdateDate(date);

        Gson gson = new Gson();

//        DBObject dbObject = BasicDBObject.parse(gson.toJson(u));
//        collection.insertOne(dbObject);

        Document document = Document.parse(gson.toJson(u));
        collection.insertOne(document);
    }

    @Test
    public void testDuplicateKey() {
        Assertions.assertThrows(MongoWriteException.class, () -> {
            testInsert();
            testInsert();
        });
    }

    @Test
    public void testFindOne() {
        Gson gson = new Gson();
        FindIterable iterable = collection.find();
        MongoCursor cursor = iterable.cursor();

        while (cursor.hasNext()) {
            Object obj = cursor.next();
            Document doc = (Document) obj;
            String json = doc.toJson(JsonWriterSettings.builder().build());
            User u = gson.fromJson(json, User.class);
            log.debug("{}", u);
        }
    }

    @Test
    public void testDropCollection() {
        MongoDatabase database = client.getDatabase(MongoClientTest.DATABASE);
        MongoCollection<Document> collection = database.getCollection(MongoClientTest.COLLECTION);
        if (null != collection) collection.drop();

        Assertions.assertNull(database.getCollection(MongoClientTest.COLLECTION));
    }
}
