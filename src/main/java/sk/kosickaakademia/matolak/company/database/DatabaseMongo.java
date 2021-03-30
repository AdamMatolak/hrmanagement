package sk.kosickaakademia.matolak.company.database;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import sk.kosickaakademia.matolak.company.entity.User;
import sk.kosickaakademia.matolak.company.log.Log;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

    public class DatabaseMongo {
        Log log = new Log();
        private static final MongoClient mongoClient = new MongoClient();
        private static MongoDatabase db;
        private static Document document;

        public void insertNewUser(User user){
            db=mongoClient.getDatabase("myFirstDB");
            document.append("fname", user.getFname());
            document.append("lname", user.getLname());
            document.append("age", user.getAge());
            document.append("gender", user.getGender().getValue());
            db.getCollection("users").insertOne(document);


        }

    }
