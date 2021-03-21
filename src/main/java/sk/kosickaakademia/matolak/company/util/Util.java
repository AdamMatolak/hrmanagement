package sk.kosickaakademia.matolak.company.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import sk.kosickaakademia.matolak.company.entity.User;
import sk.kosickaakademia.matolak.company.enumerator.Gender;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;


public class Util {
    public String getJson(List<User> list){
        /*  ak user==null   { }
            { "datetime":"1254-12-25..." , "size":1 , "users":[ { },{ },{ },{ } ] }
         */
        if (list != null) {
            JSONObject object = new JSONObject();
            object.put("datetime",getCurrentDateTime());
            object.put("size",list.size());
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
        }else {
            return "{}";
        }
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
    public String normalizeName(String name) {
        //ĎOĎO -> Ďoďo
        if (name == null || name.equals("")) {
            return " ";
        }
        return Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase();
    }
    public String getOverview(List<User> list){
        int count = list.size();
        int male = 0;
        int female = 0;
        int sumage = 0;
        int min = count>0? list.get(0).getAge():0;
        int max = count>0? list.get(0).getAge():0;
        for (User user : list){
            if (user.getGender()== Gender.MALE){
                male++;
            }else if (user.getGender()== Gender.FEMALE) {
                female++;
            }
            sumage+= user.getAge();
            if (min>user.getAge()){
                min=user.getAge();
            }
            if (max<user.getAge()){
                max = user.getAge();
            }
            double avg = (double)sumage/count;
            JSONObject obj = new JSONObject();
            obj.put("count",count);
            obj.put("min",min);
            obj.put("max",max);
            obj.put("countMale",male);
            obj.put("countFemale",female);
            obj.put("averageAge",avg);
            return obj.toJSONString();
        }
        return null;
    }
    public String generateToken(){
        // 40 znakov,male a velké písmená a čislice
        String token="";
        Random rnd = new Random();
        for(int i = 0;i < 40;i++){
            int x= rnd.nextInt(3);//0 velké, 1 malé, 2 cislo
            switch (x){
                case 0:token=token+(char)(rnd.nextInt(26)+65); break;
                case 1:token=token+(char)(rnd.nextInt(26)+97); break;
                case 2:token=token+(char)(rnd.nextInt(10)+48); break;
            }
        }
        return token;
    }
}
