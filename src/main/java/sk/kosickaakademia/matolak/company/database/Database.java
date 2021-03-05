package sk.kosickaakademia.matolak.company.database;

import sk.kosickaakademia.matolak.company.entity.User;
import sk.kosickaakademia.matolak.company.log.Log;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Database {

    Log log = new Log();
    private final String INSERTQUERY ="INSERT INTO user (fname, lname, age, gender) " + " VALUES (?, ?, ?, ?)";
    public Connection getConnection(){
        try {
            Properties props = new Properties();
            InputStream loader = getClass().getClassLoader().getResourceAsStream("db.properties");
            props.load(loader);
            String url = props.getProperty("url");
            String username=props.getProperty("username");
            String password=props.getProperty("password");
            Connection con = DriverManager.getConnection(url, username, password);
            log.print("Connection: success!");
            return con;
        }catch (SQLException | IOException ex){
            log.error(ex.toString());
        }
        return(null);
    }
    public void closeConnection(Connection con){
        if(con!=null){
            try{
                con.close();
                log.print("connection closed");
            }catch (SQLException e){
                log.error(e.toString());
            }
        }
    }
    public boolean insertNewUser(User user){
        Connection con = getConnection();
        if (con!=null){
            try {
                PreparedStatement ps = con.prepareStatement(INSERTQUERY);
                ps.setString(1,user.getFname());
                ps.setString(2,user.getLname());
                ps.setInt(3,user.getAge());
                ps.setInt(4,user.getGender().getValue());
                int result = ps.executeUpdate();
                closeConnection(con);
                log.print("New user has been added to the DB");
                return result==1;
            }catch (SQLException ex){
                log.error(ex.toString());
            }
        }
        return false;
    }
    public List<User> getFemales(){
        String sql = "SELECT * FROM user WHERE gender = 1";
        try{
            PreparedStatement ps = getConnection().prepareStatement(sql);
            return executeSelect(ps);
        }catch (Exception ex){
            log.error(ex.toString());
        }
        return null;
    }
    public List<User> getMales(){
        String sql = "SELECT * FROM user WHERE gender=0";
        try{
            PreparedStatement ps = getConnection().prepareStatement(sql);
            return executeSelect(ps);
        }catch (Exception ex){
            log.error(ex.toString());
        }
        return null;
    }
    public List<User> getUsersByAge(int from, int to){
        if (to<from){
            return null;
        }
        try{
            String sql = "SELECT * FROM user WHERE age >= ? AND age <= ?";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1,from);
            ps.setInt(2, to);
            return executeSelect(ps);
        }catch (Exception ex){
            log.error(ex.toString());
        }
        return null;
    }
    private List<User> executeSelect(PreparedStatement ps) throws SQLException{
        ResultSet rs = ps.executeQuery();
        List<User> list = new ArrayList<>();
        int count = 0;
        while (rs.next()){
            count ++;
            String fname = rs.getString("fname");
            String lname = rs.getString("lname");
            int age = rs.getInt("age");
            int id = rs.getInt("id");
            int gender = rs.getInt("gender");
            User u=new User(id,fname,lname,age,gender);
            list.add(u);
        }
        System.out.println("Number of records: " + count);
        return list;
    }
}
