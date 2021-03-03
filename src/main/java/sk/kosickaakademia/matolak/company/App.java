package sk.kosickaakademia.matolak.company;

import sk.kosickaakademia.matolak.company.database.Database;
import sk.kosickaakademia.matolak.company.entity.User;
import sk.kosickaakademia.matolak.company.enumerator.Gender;

import java.util.List;

public class App 
{
    public static void main(String[] args) {
        System.out.println("Hello world");
        Database db=new Database();
        db.insertNewUser(new User("Roman","Banik",35, 0));
        List<User> list = db.getMales();
        System.out.println(list);
    }
}
