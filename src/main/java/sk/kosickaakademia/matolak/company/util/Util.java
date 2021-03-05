package sk.kosickaakademia.matolak.company.util;

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
        return null;
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
        return name;
    }
}
