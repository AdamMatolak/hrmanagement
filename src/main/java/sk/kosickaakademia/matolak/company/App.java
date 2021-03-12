package sk.kosickaakademia.matolak.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sk.kosickaakademia.matolak.company.database.Database;
import sk.kosickaakademia.matolak.company.entity.User;
import sk.kosickaakademia.matolak.company.enumerator.Gender;
import sk.kosickaakademia.matolak.company.util.Util;

import java.util.List;

@SpringBootApplication
public class App 
{
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}
