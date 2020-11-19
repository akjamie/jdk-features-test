package org.akj.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.apache.commons.lang.StringEscapeUtils;
import org.bson.BsonNull;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MongoAggregationTest {
    private MongoClient client;

    private static final String DB_URL = "mongodb://localhost:27017";

    private static final String DATABASE_NAME = "test";

    private static final String COLLECTION = "orders";

    private MongoCollection collection;

    @BeforeEach
    public void setup() {
        MongoClientURI clientURI = new MongoClientURI(DB_URL);
        client = new MongoClient(clientURI);

        MongoDatabase database = client.getDatabase(DATABASE_NAME);

        collection = database.getCollection(COLLECTION);
    }

    @Test
    public void aggregate() throws ParseException {
        //2> calculate the total amount and order count for 1st quarter of 2019
        //db.orders.aggregate([
        //{$match: {"status": "completed",
        //          "orderDate": {$gte: ISODate('2019-01-01'), $lte: ISODate('2019-03-31')}}},
        //{$group: {"_id": null,
        //          "totalAmount":{$sum: "$total"},
        //          "totalShippingFee": {$sum: "$shippingFee"},
        //          "orderCount": {$sum: 1}}},
        //{$project: {"grandTotalAmount":{$add: ["$totalAmount","$totalShippingFee"]},
        //            "orderCount": 1,"_id": 0}}
        //])
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));

        AggregateIterable o = collection.aggregate(Arrays.asList(match(Filters.and(eq("status", "completed"), Filters.gte("orderDate",
                format.parse("2019-01-01 00:00:00")), Filters.lte("orderDate", format.parse("2019-12-31 23:59:59")))),
                group(new BsonNull(), sum("totalAmount", "$total"), sum("totalShippingFee", "$shippingFee"), sum("orderCount", 1)),
                project(fields(computed("grandTotalAmount", eq("$add", Arrays.asList("$totalAmount", "$totalShippingFee"))),
                        computed("orderCount", "$orderCount"), excludeId()))));
        Document result = (Document) Optional.ofNullable(o.first()).orElse(null);
        assertNotNull(result);
        assertEquals(new BigDecimal("8964778.00"), ((org.bson.types.Decimal128) result.get("grandTotalAmount")).bigDecimalValue());
        assertEquals(20015, result.get("orderCount"));
    }

    @Test
    public void test() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        System.out.println(format.format(new Date()));

        String a = "Hsbc (Limited) Cooperation";

        String b = "Hsbc is a global (finance institute, Hsbc (Limited) Cooperation, testing message";
        String c = "Hsbc is a global (finance institute";

        b.replaceAll(a,"***");
        b.replace(c,"-------");
    }
}
