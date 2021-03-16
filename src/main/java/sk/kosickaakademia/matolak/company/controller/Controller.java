package sk.kosickaakademia.matolak.company.controller;

import org.graalvm.compiler.lir.LIRInstruction;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/users/{gender}")
    public ResponseEntity<String> getUsersByGender(@PathVariable String gender){
        if(gender=="male"){
            List<User> list=new Database().getMales();
            String json = new Util().getJson(list);
            return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(json);
        }
        else if(gender=="female"){
            List<User> list = new Database().getFemales();
            String json = new Util().getJson(list);
            return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(json);
        }else {
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(null);
        }
    }
    @GetMapping("/users/age")
    public ResponseEntity<String> usersByAge(@RequestParam(value="from")int a,@RequestParam(value="to")int b) {
        if (a > b) {
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("");
        }
        List<User> list = new Database().getUsersByAge(a, b);
        String json = new Util().getJson(list);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(json);
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<String> changeUserAge(@PathVariable int id,@RequestBody String body) {
        JSONObject o=new JSONObject();
        try {
            o= (JSONObject) new JSONParser().parse(body);
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("");
        }catch(ParseException e) {
            e.printStackTrace();
        }
        String data=String.valueOf(o.get("newAge"));
        System.out.println("data:"+data);
        if(data.equalsIgnoreCase("null")){
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("");
        }
        int newAge = Integer.parseInt(data);
        if(newAge<1){
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body("");
        }
        boolean result = new Database().changeAge(id,newAge);
        if(result){
            return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body("");
        }else{
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body("");
        }
    }
}
