/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.kln.mit.restapi.controller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

    private static final String SEARCH_USER = "searchUser";   
    private static final String INSERT_USER = "insertUser";
    private static final String UPDATE_USER = "updateUser";
    private static final String DELETE_USER = "deleteUser";
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public UserController() {
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String Router(String request){
        JsonElement rootElement = JsonParser.parseString(request);
        JsonObject jsonObject = rootElement.getAsJsonObject();
        
        //de-serialization of object
        JsonElement requestId = jsonObject.get("requestId");        
        String requestMethod = jsonObject.get("method").toString();
        
        requestMethod = requestMethod.replace("\"", "");
        System.out.println(requestMethod);
        
        String string = String.format("Hi I received it\nHere are request details.\nRequest Id:%s\nRequest method:%s",requestId,requestMethod);
        
        switch(requestMethod){
            case "searchUser":
                string+="\nworking on search user method";
                break;
            default:
               string+="\nMethod not found";
            
        }
        return string;
        
    }

    
    
    public String getAllUser(String request) {
        
        String newString = "{'requestId':'54564','requestDate':'2022-12-11','method':'searchUser','user':{'name':'lasith','age':'23'}}";
        
        //create a json element and parse the json string to a json element
        JsonElement jsonElement = JsonParser.parseString(newString);
        JsonElement element02 = JsonParser.parseString(request);
        
        //then convert it to a json object
        JsonObject jsonObj = jsonElement.getAsJsonObject();
        JsonObject obj2 = element02.getAsJsonObject();
        
        
        String newjson = new Gson().toJson(jsonElement);
        System.out.println(newjson);
        return new Gson().toJson(obj2);

//        List<User> users = User.find();
//        if(users.size() > 0){
//            return new Gson().toJson(users);
//
//        }else{
//            return new Gson().toJson("{\"message\":\"No users found\"}");
//        }
        
    }
    
    
    public String getAllUsers(String nic) {
        
        User user = User.find(nic);
        
        if(user != null){
           return new Gson().toJson(user);
        }else{
           return new Gson().toJson("{\"message\":\"No user found\"}");
        }
        
    }
    
   
    public String newUser(User user) {
     
      return User.save(user);
        
    }
    
    
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateUser(User user, String oldNic) {
       
        return User.update(user,oldNic);    
        
    }
    
     
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String removeUser(String nic) {
        
        return User.remove(nic);
        
    }
}
