/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.kln.mit.restapi.model;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import lk.kln.mit.restapi.Database.Database;

/**
 *
 * @author Lasith
 */
public class User {

    private String nic;       
    private String oldNic;    
    private String fullName;
    private String address;
    private Date dob;
    private String nationality;
    private String gender;
    private int age;
    
    public User(){
        
    };
    
    public User(String nic) {
        this.nic = nic;
    }

    public User(String nic, String fullName, String address, Date dob, String nationality, String gender) {
        this.nic = nic;
        this.fullName = fullName;
        this.address = address;
        this.dob = dob;
        this.nationality = nationality;
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    public String getOldNic() {
        return oldNic;
    }

    public void setOldNic(String oldNic) {
        this.oldNic = oldNic;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public static List<User> find(){
        List <User> users = new ArrayList();
        User user;
        String query = "SELECT * FROM user";
        ResultSet resultSet;
        try{
            Connection conn = Database.getConnection();
            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                // String nic, String fullName, String address, Date dob, String nationality, String gender
               user = new User(
                    resultSet.getString("nic"),
                    resultSet.getString("full_name"),
                    resultSet.getString("address"),
                    resultSet.getDate("dob"),
                    resultSet.getString("nationality"),
                    resultSet.getString("gender")                    
                    );
               users.add(user);
                System.out.println(user);
            }

        }
        catch(Exception e){
            e.printStackTrace();
            //model.addAttribute("message", "Couldn't receive user details.");
        }
        return users;
    }
    
    
    public static User find(String nic){

        User user = null;
        String query = "SELECT * FROM user WHERE nic = '"+nic+"'";
        ResultSet resultSet;
        try{
            Connection conn = Database.getConnection();
            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
               user = new User(
                    resultSet.getString("nic"),
                    resultSet.getString("full_name"),
                    resultSet.getString("address"),
                    resultSet.getDate("dob"),
                    resultSet.getString("nationality"),
                    resultSet.getString("gender")                    
                    );
            }
        }
        catch(Exception e){
            e.printStackTrace();
            //model.addAttribute("message", "Couldn't receive user details.");
        }
        return user;
    }
    
    
    public static String save(User user){
        
        java.util.Date date = new java.util.Date();
        java.sql.Date today = new java.sql.Date(date.getTime());
        String query = "INSERT INTO `user`(`nic`, `full_name`, `address`, `dob`, `nationality`, `gender`,`state`, `action_performed_by`, `record_date`) VALUES ('"+user.getNic()+"','"+user.getFullName()+"','"+user.getAddress()+"','"+user.getDob()+"','"+user.getNationality()+"','"+user.getGender()+"', '1','system','"+today+"')";
        
        //String query = "INSERT INTO `user`(`nic`, `full_name`, `address`, `dob`, `nationality`, `gender`) VALUES ('"+user.getNic()+"','"+user.getFullName()+"','"+user.getAddress()+"','"+user.getDob()+"','"+user.getNationality()+"','"+user.getGender()+"')";
        try(Connection conn = Database.getConnection()){

            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            return new Gson().toJson("{\"message\":\"User details saved!\"}");

        }
        catch(Exception e){
            return new Gson().toJson("{\"message\":\"Could not save user details\"}");
        }
    }
    
    public static String update(User user, String oldNic){
        
        java.util.Date date = new java.util.Date();
        java.sql.Date today = new java.sql.Date(date.getTime());
        String query = "UPDATE `user` SET `nic`='"+user.getNic()+"',`full_name`='"+user.getFullName()+"',`address`='"+user.getAddress()+"',`dob`='"+user.getDob()+"',`nationality`='"+user.getNationality()+"',`gender`='"+user.getGender()+"',`state`='1', `action_performed_by` = 'system', `record_date`='"+today+"' WHERE nic='"+oldNic+"'";
           
        //String query = "UPDATE `user` SET `nic`='"+user.getNic()+"',`full_name`='"+user.getFullName()+"',`address`='"+user.getAddress()+"',`dob`='"+user.getDob()+"',`nationality`='"+user.getNationality()+"',`gender`='"+user.getGender()+"' WHERE nic='"+user.getOldNic()+"'";
            try(Connection conn = Database.getConnection()){

                Statement statement = conn.createStatement();
                statement.executeUpdate(query);
                return "{\"message\":\"User updated!\"}";

            }
            catch(Exception e){
                return new Gson().toJson("{\"message\":\"Could not update user details\"}");
            }
    }
    
    public static String remove(String nic){
    
        String query = "DELETE FROM `user` WHERE nic='"+nic+"'";
        try(Connection conn = Database.getConnection()){

            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            return "{\"message\":\"User removed!\"}";

        }
        catch(Exception e){
            
            return new Gson().toJson("{\"message\":\""+e.getMessage()+"\"}");


        }
    }

}
