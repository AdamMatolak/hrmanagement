package sk.kosickaakademia.matolak.company;

import sk.kosickaakademia.matolak.company.database.Database;

public class App 
{
    public static void main(String[] args) {
        System.out.println("Hello world");
        Database db=new Database();
        db.getConnection();
    }
}
