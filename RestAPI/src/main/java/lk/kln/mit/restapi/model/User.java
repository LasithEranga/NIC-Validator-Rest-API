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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        String query = "SELECT * FROM user WHERE state=1";
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
        String query = "SELECT * FROM user WHERE nic = '"+nic+"' AND state=1";
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
        boolean isNicDobGenderValid = validateNicDobGender(user.getNic(),user.getDob().toString(),user.getGender());
        boolean isNameValid = validName(user.getFullName());
        boolean isAddressValid = validAddress(user.getAddress());
        java.util.Date date = new java.util.Date();
        java.sql.Date today = new java.sql.Date(date.getTime());

        System.out.println(today);
        if(isNicDobGenderValid && isNameValid && isAddressValid){
        
        String query = "INSERT INTO `user`(`nic`, `full_name`, `address`, `dob`, `nationality`, `gender`,`state`, `action_performed_by`, `record_date`) VALUES ('"+user.getNic()+"','"+user.getFullName()+"','"+user.getAddress()+"','"+user.getDob()+"','"+user.getNationality()+"','"+user.getGender()+"',1,'system','"+today+"')";
        
        //String query = "INSERT INTO `user`(`nic`, `full_name`, `address`, `dob`, `nationality`, `gender`) VALUES ('"+user.getNic()+"','"+user.getFullName()+"','"+user.getAddress()+"','"+user.getDob()+"','"+user.getNationality()+"','"+user.getGender()+"')";
        try(Connection conn = Database.getConnection()){

            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            return new Gson().toJson("{\"message\":\"User details saved!\"}");

        }
        catch(Exception e){
            return new Gson().toJson("{\"message\":\"Could not save user details\"}");
        }
        }else{
            return new Gson().toJson("{\"message\":\"Validation error\"}");
        }
    }
    
    public static String update(User user, String oldNic){
        boolean isNicDobGenderValid = validateNicDobGender(user.getNic(),user.getDob().toString(),user.getGender());
        boolean isNameValid = validName(user.getFullName());
        boolean isAddressValid = validAddress(user.getAddress());
        java.util.Date date = new java.util.Date();
        java.sql.Date today = new java.sql.Date(date.getTime());

        if(isNicDobGenderValid && isNameValid && isAddressValid){

        String query = "UPDATE `user` SET `nic`='"+user.getNic()+"',`full_name`='"+user.getFullName()+"',`address`='"+user.getAddress()+"',`dob`='"+user.getDob()+"',`nationality`='"+user.getNationality()+"',`gender`='"+user.getGender()+"',`state`=1, `action_performed_by` = 'system', `record_date`='"+today+"' WHERE nic='"+oldNic+"'";
           
        //String query = "UPDATE `user` SET `nic`='"+user.getNic()+"',`full_name`='"+user.getFullName()+"',`address`='"+user.getAddress()+"',`dob`='"+user.getDob()+"',`nationality`='"+user.getNationality()+"',`gender`='"+user.getGender()+"' WHERE nic='"+user.getOldNic()+"'";
            try(Connection conn = Database.getConnection()){

                Statement statement = conn.createStatement();
                statement.executeUpdate(query);
                return "{\"message\":\"User updated!\"}";

            }
            catch(Exception e){
                return new Gson().toJson("{\"message\":\"Could not update user details\"}");
            }
        }else{
            return new Gson().toJson("{\"message\":\"Validation error\"}");
        }
    }
    
    public static String remove(String nic){
    
        java.util.Date date = new java.util.Date();
        java.sql.Date today = new java.sql.Date(date.getTime());
        //String query = "DELETE FROM `user` WHERE nic='"+nic+"'";
        String query = "UPDATE `user` SET `state`=0,`action_performed_by`='system', `record_date`='"+today+"'  WHERE nic='"+nic+"'";
        
        try(Connection conn = Database.getConnection()){

            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            return "{\"message\":\"User removed!\"}";

        }
        catch(Exception e){
            
            return new Gson().toJson("{\"message\":\""+e.getMessage()+"\"}");


        }
    }
    
    public static boolean validateNicDobGender(String nic,String submittedDob, String submittedGender){

            int months [] = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            Pattern oldNicPattern = Pattern.compile("^\\d{9}[vxVX]{1}");
            Pattern newNicPattern = Pattern.compile("^\\d{12}");
            Matcher matcher = null;

            boolean validNic = false;
            boolean oldNIC = false;
          
            if (nic.length() == 0) {
          
              return false;
          
            } else {
              if (nic.length() == 12) {
                matcher = newNicPattern.matcher(nic);
                if(matcher.find()){
                    validNic = true;
                    oldNIC = false;
                }else{
                    return false;
                }
                
              } else if (nic.length() == 10) {
                matcher = oldNicPattern.matcher(nic);
                if(matcher.find()){

                    validNic = true;
                    oldNIC = true;
                }else{
                    return false;
                }
                
              }
          
              String gender = "";
              int year;
              int month = 0;
              int date;
              int monthDate;
              int yearDigits;
          
              if (validNic) {
          
                if (oldNIC) {
                  yearDigits =Integer.parseInt(nic.substring(0, 2));
                  monthDate = Integer.parseInt(nic.substring(2, 5));
                  year = 1900 + yearDigits;
                } else {
                  yearDigits = Integer.parseInt(nic.substring(0, 4));
                  monthDate = Integer.parseInt(nic.substring(4, 7));
                  year = yearDigits;
                }
          
                if (monthDate > 500) {
                  monthDate = monthDate - 500;
                  gender = "Female";
                } else {
                  gender = "Male";
                }
          
                int tempMd = monthDate;
          
                for (int i = 0; i < 12; i++) {
                  if (tempMd <= months[i]) {
                    month = i + 1;
                    break;
                  } else {
                    tempMd = tempMd - months[i];
                  }
                }
          
                // date
                for (int i = 0; i < month - 1; i++) {
                  monthDate = monthDate - months[i];
                }
                date = monthDate;
                String yearStr = Integer.toString(year);
                String monthStr = Integer.toString(month);
                if(monthStr.length() != 2){
                    monthStr = "0"+monthStr;
                }
                String dateStr = Integer.toString(date);
                String generatedDob = yearStr+"-"+monthStr+"-"+dateStr;
                //compare submitted values and return
                if(submittedDob.equals(generatedDob) && submittedGender.equals(gender)){
                    return true;
                }else{
                    return false;
                }
              } else {
                return false;
              }
            }
    }

    public static boolean validName(String name){
        Pattern namePattern = Pattern.compile("^[a-zA-Z]");
        Matcher matcher = namePattern.matcher(name);
        if(matcher.find()){
            return true;
        }else{
            return false;
        }
    }

    public static boolean validAddress(String address){
        Pattern addressPattern = Pattern.compile("^[a-zA-Z0-9/.,]");
        Matcher matcher = addressPattern.matcher(address);
        if(address.length() == 0){
            return false;
        }
        if(matcher.find()){
            return true;
        }else{
            return false;
        }
    }


}
