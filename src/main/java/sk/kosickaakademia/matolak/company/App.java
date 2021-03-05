package sk.kosickaakademia.matolak.company;

import sk.kosickaakademia.matolak.company.database.Database;
import sk.kosickaakademia.matolak.company.entity.User;
import sk.kosickaakademia.matolak.company.enumerator.Gender;
import sk.kosickaakademia.matolak.company.util.Util;

import java.util.List;

public class App 
{
    public static void main(String[] args) {
        Database db=new Database();
        List<User> list = db.getUsersByAge(10,35);


    }
}
