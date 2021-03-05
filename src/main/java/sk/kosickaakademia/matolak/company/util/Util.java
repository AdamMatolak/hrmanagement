package sk.kosickaakademia.matolak.company.util;

import org.graalvm.compiler.lir.LIRInstruction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sk.kosickaakademia.matolak.company.entity.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class Util {
    public String getJson(List<User> list){
        /*  ak user==null   { }
            { "datetime":"1254-12-25..." , "size":1 , "users":[ { },{ },{ },{ } ] }

         */
        if (list.isEmpty()) return "{}";

        JSONObject object = new JSONObject();
        object.put("datetime",getCurrentDateTime());
        object.put("size",1);
        JSONArray jsonArray = new JSONArray();
        for (User u : list){
            JSONObject userJson = new JSONObject();
            userJson.put("id",u.getId());
            userJson.put("fname",u.getFname());
            userJson.put("lname",u.getLname());
            userJson.put("age",u.getAge());
            userJson.put("gender",u.getGender().toString());
            jsonArray.add(userJson);

        }
        object.put("users",jsonArray);

        return object.toJSONString();
    }
    public String getJson(User user){
        /*  ak user==null   { }
            { "datetime":"1254-12-25..." , "size":1 , "users":[ {"id",  } ] }

         */
        if (user==null) return "{}";
        JSONObject object = new JSONObject();
        object.put("datetime",getCurrentDateTime());
        object.put("size",1);
        JSONArray jsonArray = new JSONArray();
        JSONObject userJson = new JSONObject();
        userJson.put("id",user.getId());
        userJson.put("fname",user.getFname());
        userJson.put("lname",user.getLname());
        userJson.put("age",user.getAge());
        userJson.put("gender",user.getGender().toString());
        jsonArray.add(userJson);
        object.put("users",jsonArray);

        return object.toJSONString();
    }
    public String getCurrentDateTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatOfCurrentDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedCurrentTimeDate = currentDateTime.format(formatOfCurrentDateTime);
        return formattedCurrentTimeDate; //"2021-03-05 15:07:23"
    }
    public String normalizeName(String name){
        //ĎOĎO -> Ďoďo
        if (name==null||name.equals("")){
            return " ";
        }
        return Character.toUpperCase(name.charAt(0))+name.substring(1).toLowerCase();
    }
}
