package sk.kosickaakademia.matolak.company;

import sk.kosickaakademia.matolak.company.database.Database;
import sk.kosickaakademia.matolak.company.entity.User;
import sk.kosickaakademia.matolak.company.enumerator.Gender;

public class App 
{
    public static void main(String[] args) {
        System.out.println("Hello world");
        Database db=new Database();
        db.insertNewUser(new User("Roman","Banik",35, 0));
    }
}
