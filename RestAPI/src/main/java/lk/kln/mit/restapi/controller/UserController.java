/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.kln.mit.restapi.controller;
import com.google.gson.Gson;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import lk.kln.mit.restapi.model.User;

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
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllUsers() {

        List<User> users = User.find();
        if(users.size() > 0){
            return new Gson().toJson(users);

        }else{
            return new Gson().toJson("{\"message\":\"No users found\"}");
        }
        
    }
    
    @GET
    @Path("{nic}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllUsers(@PathParam("nic") String nic) {
        
        User user = User.find(nic);
        
        if(user != null){
           return new Gson().toJson(user);
        }else{
           return new Gson().toJson("{\"message\":\"No user found\"}");
        }
        
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String newUser(User user) {
        
        return User.save(user);
        
    }
    
    @POST
    @Path("{oldNic}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateUser(User user,@PathParam("oldNic") String oldNic) {
       
        return User.update(user,oldNic);    
        
    }
    
    @DELETE
    @Path("{nic}")    
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String removeUser(@PathParam("nic") String nic) {
        
        return User.remove(nic);
        
    }
}
