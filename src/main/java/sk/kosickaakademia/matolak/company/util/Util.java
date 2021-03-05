package sk.kosickaakademia.matolak.company.util;

import sk.kosickaakademia.matolak.company.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class Util {
    public String getJson(List<User> list){

        return null;
    }
    public String getJson(User user){

        return null;
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
