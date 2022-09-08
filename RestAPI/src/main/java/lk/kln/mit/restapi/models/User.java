/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.kln.mit.restapi.models;

import java.sql.Date;

/**
 *
 * @author Lasith
 */
public class User {
       
    private String nic;
    private String fullName;
    private String address;
    private Date dob;
    private String nationality;
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User(){
        
    };

    public User(String nic, String fullName, String address, Date dob, String nationality, String gender) {
        this.nic = nic;
        this.fullName = fullName;
        this.address = address;
        this.dob = dob;
        this.nationality = nationality;
        this.gender = gender;
    }

    private String gender;


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


}
