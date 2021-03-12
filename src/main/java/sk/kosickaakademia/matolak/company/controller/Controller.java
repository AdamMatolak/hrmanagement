package sk.kosickaakademia.matolak.company.controller;

import org.graalvm.compiler.lir.LIRInstruction;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sk.kosickaakademia.matolak.company.database.Database;
import sk.kosickaakademia.matolak.company.entity.User;
import sk.kosickaakademia.matolak.company.enumerator.Gender;
import sk.kosickaakademia.matolak.company.log.Log;
import sk.kosickaakademia.matolak.company.util.Util;

import java.util.List;

@RestController
public class Controller {
    Log log = new Log();

    @PostMapping("/user/new")
    public ResponseEntity<String> insertNewUser(@RequestBody String data){
        try {
            JSONObject object = (JSONObject) new JSONParser().parse(data);

            String fname = ((String)object.get("fname"));
            String lname = ((String)object.get("lname"));
            String gender = (String)object.get("gender");
            Integer age = Integer.parseInt(String.valueOf(object.get("age")));
            if (fname==null||lname==null||lname.trim().length()==0||fname.trim().length()==0||age<1){
                log.error("Missing fname or lname or incorrect age in the body of the request");
                JSONObject obj = new JSONObject();
                obj.put("error","Missing fname or lname or incorrect age in the body of the request");
                return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(obj.toJSONString());
            }
            Gender g;
            if (gender==null){
                g=Gender.OTHER;
            }else if (gender.equalsIgnoreCase("male")){
                g=Gender.MALE;
            }else if (gender.equalsIgnoreCase("female")){
                g=Gender.FEMALE;
            }else {
                g=Gender.OTHER;
            }
            User user = new User(fname,lname,age,g.getValue());
            new Database().insertNewUser(user);

            String newJoke = (String) object.get("user");
            if (newJoke==null){
                return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("{}");
            }
        } catch (Exception e) {
            log.error("Cannot process input data in /user/new controller");
            JSONObject obj = new JSONObject();
            obj.put("error","Cannot process input data in /user/new controller");
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(obj.toJSONString());
        }
        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(null);
    }

    @GetMapping("/users")
    public ResponseEntity<String> getAllUsers(){
        List<User> list = new Database().getAllUsers();
        String json = new Util().getJson(list);
        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(json);
    }
}
