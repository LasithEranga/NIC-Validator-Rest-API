/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.kln.mit.restapi.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import lk.kln.mit.restapi.Database.Database;

/**
 * REST Web Service
 *
 * @author Lasith
 */
@Path("user")
public class UserController {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public UserController() {
    }

    /**
     * Retrieves representation of an instance of lk.kln.mit.restapi.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("all-users")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllUsers() {
        String users = "[";
        String query = "SELECT * FROM user";
        ResultSet resultSet = null;
        try(Connection conn = Database.getConnection()){

            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                // String nic, String fullName, String address, Date dob, String nationality, String gender
                users += "{'nic':'"+resultSet.getString("nic")+"',";
                users += "'fullName':'"+resultSet.getString("full_name")+"',";
                users += "'address':'"+resultSet.getString("address")+"',";
                users += "'dob':'"+resultSet.getString("dob")+"',";
                users += "'nationality':'"+resultSet.getString("nationality")+"',";
                users += "'gender':'"+resultSet.getString("gender")+"'},";
            }

        }
        catch(Exception e){
            // e.printStackTrace();
            //model.addAttribute("message", "Couldn't receive user details.");

        }
        users.substring(0, users.length()-1);
        users += "]";
        System.out.println(users);
        return "{\"message\":\"lasith\"}";
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
