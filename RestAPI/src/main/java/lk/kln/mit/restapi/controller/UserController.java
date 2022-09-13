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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
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
    @Path("all-users")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllUsers(@Context HttpHeaders header) {
        MultivaluedMap<String, String> headerParams = header.getRequestHeaders();
        System.out.println(headerParams);
        List<User> users = User.find();
        
        return new Gson().toJson(users);
        
    }
    
    @POST
    @Path("new-user")    
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String newUser(User user) {
        
        return User.save(user);
        
    }
     
    @POST
    @Path("update-user")    
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateUser(User user) {
        return User.update(user);       
    }
    
    @POST
    @Path("remove-user")    
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String removeUser(User user) {
        
        return User.remove(user.getNic());
        
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
