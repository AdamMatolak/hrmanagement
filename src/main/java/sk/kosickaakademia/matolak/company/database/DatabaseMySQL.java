package sk.kosickaakademia.matolak.company.database;

import sk.kosickaakademia.matolak.company.entity.User;
import sk.kosickaakademia.matolak.company.log.Log;
import sk.kosickaakademia.matolak.company.util.Util;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatabaseMySQL {

    Log log = new Log();
    private final String query="INSERT INTO user (fname, lname, age, gender) VALUES ( ?, ?, ?, ?)";
    private final String female="SELECT * FROM user WHERE gender = 1";
    private final String male="SELECT * FROM user WHERE gender = 0";
    private final String other="SELECT * FROM user WHERE gender = 2";
    private final String usersId="SELECT * FROM user WHERE id = ?";
    private final String userPattern="SELECT * FROM user WHERE (fname, lname) = ?";
    private final String allUsers="SELECT * FROM user ";
    private final String newUserAge = "UPDATE user SET age = ? WHERE id = ?";
    private final String usersByAge ="SELECT * FROM user WHERE age >= ? AND age <= ?";
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
            System.out.println("Connection navázáno");
            return con;
        }catch (Exception ex){
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
        if (user==null){
            return false;
        }
        String fname = new Util().normalizeName(user.getFname());
        String lname = new Util().normalizeName(user.getLname());
        Connection con = getConnection();
        if (con!=null){
            try {
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1,fname);
                ps.setString(2,lname);
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
        log.info("Executing: getFemales()");
        String sql = "SELECT * FROM user WHERE gender = 1";
        try(Connection con = getConnection()){
            if (con!=null){
                PreparedStatement ps = getConnection().prepareStatement(sql);
                return executeSelect(ps);
            }
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
        log.info("Number of records: " + count);
        return list;
    }
    public List<User> getAllUsers(){

        String sql = "SELECT * FROM user";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            return executeSelect(ps);
        }catch(Exception ex){
            log.error(ex.toString());
        }
        return null;
    }
    public User getUserById(int id){

        String sql = "SELECT * FROM user WHERE id = ?";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1,id);
            List<User> list = executeSelect(ps);
            if(list.isEmpty()) {
                return null;
            }
            else {
                list.get(0);
            }
        }catch(Exception ex){
            log.error(ex.toString());
        }
        return null;
    }
    public boolean changeAge(int id, int newAge){
        if (id < 0 || newAge < 1 || newAge >= 100)
            return false;
        try (Connection conn=getConnection()){
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(newUserAge);
                ps.setInt(1, newAge);
                ps.setInt(2, id);
                int update=ps.executeUpdate();
                log.print("Updated age for id: " + id);
                return update==1;
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return false;
    }
    public List<User> getUser(String pattern){
        try (Connection conn = getConnection()) {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(userPattern);
                return executeSelect(ps);
            }
        }catch(Exception ex) {
        }
        return null;
    }

}
