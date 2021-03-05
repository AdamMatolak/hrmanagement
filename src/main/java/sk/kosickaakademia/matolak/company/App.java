package sk.kosickaakademia.matolak.company;

import sk.kosickaakademia.matolak.company.database.Database;
import sk.kosickaakademia.matolak.company.entity.User;
import sk.kosickaakademia.matolak.company.enumerator.Gender;

import java.util.List;

public class App 
{
    public static void main(String[] args) {
        Database db=new Database();
        List<User> list = db.getUsersByAge(10,35);
        for(User u:list)
            System.out.println(u.toString());
    }
}
