/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.kln.mit.restapi.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import lk.kln.mit.restapi.model.User;

/**
 * REST Web Service
 *
 * @author Lasith
 */
@Path("user")
public class UserController {

    //RESPONSE CODES
    private static final int FAILED = 0;
    private static final int SUCCESS = 1;
    private static final int BAD_REQUEST = 3;
    private static final int CONFLICT = 4;
    private static final int METHOD_NOT_FOUND = 5;

    //REQUEST METHODS
    private static final String ALL_USERS = "allUsers";
    private static final String SEARCH_USER = "searchUser";
    private static final String GET_SETOF_USERS = "getSetOfUsers";
    private static final String INSERT_USER = "insertUser";
    private static final String UPDATE_USER = "updateUser";
    private static final String DELETE_USER = "deleteUser";
    private static final String COUNT_USERS = "getCount";
    private static final String COUNT_USERS_FILTERED = "filteredResult";
    private static final String COUNT_ACTIVITIES = "getActivities";
    private static final String GET_RECENT_ACTIVITIES = "getRecentActivities";
    private static final String GET_AGE_GROUP_GRAPH = "getAgeGroupGraph";    
    private static final String GET_NATIONALTY_COUNT = "getNationaltyCount";
    private static final String GET_GENDER_COUNT = "getGenderCount";


    //RESPONSE TEMPLATE
    private static final String RESPONSE_TEMPLATE = "{'responseCode':%d,'message':%s,'requestId':'%s','%s':%s";

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
    public String Router(String request) {

        try {

            String requestId;
            String response;
            String range;
            int days;
            int noOfFilteredUsers;

            JsonElement rootElement = JsonParser.parseString(request);
            JsonObject jsonObject = rootElement.getAsJsonObject();

            //de-serialization of object
            requestId = jsonObject.get("requestId").toString();
            String userObject = jsonObject.get("user").toString();

            User user = new Gson().fromJson(userObject, User.class);

            String action = jsonObject.get("action").toString();
            action = action.replace("\"", "");
            requestId = requestId.replace("\"", "");

            switch (action) {
                case ALL_USERS:

                    List<User> users = User.find();
                    if (users.isEmpty()) {

                        response = String.format(RESPONSE_TEMPLATE, FAILED, "\"No users found\"", requestId, "users", "[]");

                    } else {
                        response = String.format(RESPONSE_TEMPLATE, SUCCESS, "\"Success\"", requestId, "users", new Gson().toJson(users));
                    }
                    break;

                case SEARCH_USER:

                    User foundUser = user.getUser();
                    if (foundUser == null) {
                        response = String.format(RESPONSE_TEMPLATE, FAILED, "\"User not found\"", requestId, "users", "[]");
                    } else {
                        response = String.format(RESPONSE_TEMPLATE, SUCCESS, "\"Success\"", requestId,"users", new Gson().toJson(foundUser));
                    }
                    break;

                case COUNT_USERS:

                    int noOfUsers = User.getNoOfUsers();
                    if (noOfUsers == 0) {

                        response = String.format(RESPONSE_TEMPLATE, FAILED, "\"No users found\"",  requestId,"userCount", "0");

                    } else {
                        response = String.format(RESPONSE_TEMPLATE, SUCCESS, "\"Success\"",  requestId, "userCount",Integer.toString(noOfUsers));
                    }
                    break;

                case GET_SETOF_USERS:
                    String limit = jsonObject.get("limit").toString();
                    String offset = jsonObject.get("offset").toString();
                    limit = limit.replace("\"", "");
                    offset = offset.replace("\"", "");
                    List<User> usersSet = User.getSetOfUsers(Integer.parseInt(limit), Integer.parseInt(offset));
                    if (usersSet.isEmpty()) {

                        response = String.format(RESPONSE_TEMPLATE, FAILED, "\"No users found\"", requestId, "users", "0");

                    } else {
                        response = String.format(RESPONSE_TEMPLATE, SUCCESS, "\"Success\"", requestId, "users", new Gson().toJson(usersSet));
                    }
                    break;

                case COUNT_USERS_FILTERED:

                    range = jsonObject.get("range").toString();
                    range = range.replace("\"", "");
                    days = Integer.parseInt(range);
                    noOfFilteredUsers = User.getUserRegistrationCount(days);
                    if (noOfFilteredUsers == -1) {

                        response = String.format(RESPONSE_TEMPLATE, FAILED, "\"No users found\"", requestId, "users", "0");

                    } else {
                        response = String.format(RESPONSE_TEMPLATE, SUCCESS, "\"Success\"", requestId, "users", Integer.toString(noOfFilteredUsers));
                    }
                    break;

                case COUNT_ACTIVITIES:

                    range = jsonObject.get("range").toString();
                    range = range.replace("\"", "");
                    days = Integer.parseInt(range);
                    String activity = jsonObject.get("filterByActivity").toString();
                    activity = activity.replace("\"", "");
                    noOfFilteredUsers = User.getActivityCount(days, activity);
                    if (noOfFilteredUsers == -1) {

                        response = String.format(RESPONSE_TEMPLATE, FAILED, "\"No results found\"", requestId, "users", "0");

                    } else {
                        response = String.format(RESPONSE_TEMPLATE, SUCCESS, "\"Success\"", requestId, "users", Integer.toString(noOfFilteredUsers));
                    }
                    break;

                case GET_RECENT_ACTIVITIES:

                    List<User> usersActivity = User.getRecentActivities();
                    if (usersActivity.isEmpty()) {

                        response = String.format(RESPONSE_TEMPLATE, FAILED, "\"No results found\"", requestId, "users", "[]");

                    } else {
                        response = String.format(RESPONSE_TEMPLATE, SUCCESS, "\"Success\"", requestId, "users", new Gson().toJson(usersActivity));
                    }
                    break;
                
                    

                case GET_AGE_GROUP_GRAPH:

                    Map<String, Integer> map = User.getAgeGroupGraph();

                    if (map.isEmpty()) {

                        response = String.format(RESPONSE_TEMPLATE, FAILED, "\"No results found\"", requestId, "users", "[]");

                    } else {
                        JsonElement jsonElement = null;
                        JsonObject jObject = null;
                        response = String.format(RESPONSE_TEMPLATE, SUCCESS, "\"Success\"", requestId, "users", "[]");
//                        jsonElement = JsonParser.parseString();
//                        jsonObject = jsonElement.getAsJsonObject();
                        JsonArray xAxis = new JsonArray();
                        for (String key : map.keySet()) {
                            xAxis.add(key);
                        }
                        
                         JsonArray yAxis = new JsonArray();
                        for (int value : map.values()) {
                            yAxis.add(value);
                        }
                        //System.out.println(xAxis);
                        response += String.format(",'xAxis':%s,'yAxis':%s", new Gson().toJson(xAxis),new Gson().toJson(yAxis));
                    }
                    break;
                    
                case GET_GENDER_COUNT:

                    Map<String, Integer> genderMap = User.getGnderCount();

                    if (genderMap.isEmpty()) {

                        response = String.format(RESPONSE_TEMPLATE, FAILED, "\"No results found\"", requestId, "users", "[]");

                    } else {
                        JsonElement jsonElement = null;
                        JsonObject jObject = null;
                        response = String.format(RESPONSE_TEMPLATE, SUCCESS, "\"Success\"", requestId, "users", "[]");
//                        jsonElement = JsonParser.parseString();
//                        jsonObject = jsonElement.getAsJsonObject();
                        JsonArray xAxis = new JsonArray();
                        for (String key : genderMap.keySet()) {
                            xAxis.add(key);
                        }
                        
                         JsonArray yAxis = new JsonArray();
                        for (int value : genderMap.values()) {
                            yAxis.add(value);
                        }
                        //System.out.println(xAxis);
                        response += String.format(",'xAxis':%s,'yAxis':%s", new Gson().toJson(xAxis),new Gson().toJson(yAxis));
                    }
                    break;
                    
                    
                case GET_NATIONALTY_COUNT:

                    Map<String, Integer> nationalityMap = User.getNationalityCount();

                    if (nationalityMap.isEmpty()) {

                        response = String.format(RESPONSE_TEMPLATE, FAILED, "\"No results found\"", requestId, "users", "[]");

                    } else {
                        JsonElement jsonElement = null;
                        JsonObject jObject = null;
                        response = String.format(RESPONSE_TEMPLATE, SUCCESS, "\"Success\"", requestId, "users", "[]");
//                        jsonElement = JsonParser.parseString();
//                        jsonObject = jsonElement.getAsJsonObject();
                        JsonArray xAxis = new JsonArray();
                        for (String key : nationalityMap.keySet()) {
                            xAxis.add(key);
                        }
                        
                         JsonArray yAxis = new JsonArray();
                        for (int value : nationalityMap.values()) {
                            yAxis.add(value);
                        }
                        //System.out.println(xAxis);
                        response += String.format(",'xAxis':%s,'yAxis':%s", new Gson().toJson(xAxis),new Gson().toJson(yAxis));
                    }
                    break;
                    
                    

                case INSERT_USER:

                    switch (user.save()) {
                        case 1:
                            response = String.format(RESPONSE_TEMPLATE, SUCCESS, "\"User details saved.\"", requestId, "users", new Gson().toJson(user.getUser()));
                            break;
                        case 2:
                            response = String.format(RESPONSE_TEMPLATE, BAD_REQUEST, "\"Missing parameters. Please send all parameters\"", requestId, "users", "[]");
                            break;
                        case 4:
                            response = String.format(RESPONSE_TEMPLATE, CONFLICT, "\"Operation not allowed\"", requestId, "users", "[]");
                            break;
                        default:
                            response = String.format(RESPONSE_TEMPLATE, FAILED, "\"Could not save user details\"", requestId, "users", "[]");
                            break;
                    }

                    break;

                case UPDATE_USER:

                    switch (user.update()) {
                        case 1:
                            response = String.format(RESPONSE_TEMPLATE, SUCCESS, "\"User details updated\"", requestId,"users", new Gson().toJson(user.getUser()));
                            break;
                        case 2:
                            response = String.format(RESPONSE_TEMPLATE, BAD_REQUEST, "\"Missing parameters. Please inclue all required parameters\"", requestId, "users", "[]");
                            break;
                        case 4:
                            response = String.format(RESPONSE_TEMPLATE, CONFLICT, "\"Operation not allowed\"", requestId, "users", "[]");
                            break;
                        default:
                            response = String.format(RESPONSE_TEMPLATE, FAILED, "\"Could not update the user\"", requestId, "users", "[]");
                            break;
                    }

                    break;

                case DELETE_USER:

                    switch (user.remove()) {
                        case 1:
                            response = String.format(RESPONSE_TEMPLATE, SUCCESS, "\"User details removed\"", requestId, "users", new Gson().toJson(user));
                            break;
                        case 2:
                            response = String.format(RESPONSE_TEMPLATE, BAD_REQUEST, "\"Missing parameters, please include the unique id of the user\"", requestId, "users", "[]");
                            break;
                        default:
                            response = String.format(RESPONSE_TEMPLATE, FAILED, "\"Could not delete the user\"", requestId, "users", "[]");
                            break;
                    }

                    break;

                default:

                    response = String.format(RESPONSE_TEMPLATE, METHOD_NOT_FOUND, "\"Operation not found\"", requestId, "users", "[]");

            }

            JsonElement responseElement = JsonParser.parseString(response + "}");
            JsonObject responseObject = responseElement.getAsJsonObject();
            return new Gson().toJson(responseObject);

        } catch (JsonSyntaxException e) {
            //if json parsing failed
            JsonElement responseElement = JsonParser.parseString(String.format(RESPONSE_TEMPLATE, BAD_REQUEST, "\"Malformed JSON object\"", "Failed to retrive", "users", "[]"));
            JsonObject responseObject = responseElement.getAsJsonObject();
            return new Gson().toJson(responseObject);

        } catch (Exception e) {

            JsonElement responseElement = JsonParser.parseString(String.format(RESPONSE_TEMPLATE, FAILED, "\"An error occured from server side\"", "Failed to retrive", "users", "[]"));
            JsonObject responseObject = responseElement.getAsJsonObject();
            return new Gson().toJson(responseObject);

        }

    }

}
