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

    //RESPONSE CODES
    private static final int FAILED = 0;
    private static final int SUCCESS = 1;
    private static final int BAD_REQUEST = 3;
    private static final int CONFLICT = 4;
    private static final int METHOD_NOT_FOUND = 5;

    //REQUEST METHODS
    private static final String ALL_USERS = "allUsers";
    private static final String SEARCH_USER = "searchUser";
    private static final String INSERT_USER = "insertUser";
    private static final String UPDATE_USER = "updateUser";
    private static final String DELETE_USER = "deleteUser";

    //RESPONSE TEMPLATE
    private static final String RESPONSE_TEMPLATE = "{'responseCode':%d,'message':%s,'requestId':'%s','users':%s}";

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
        JsonElement rootElement = JsonParser.parseString(request);
        JsonObject jsonObject = rootElement.getAsJsonObject();

        //de-serialization of object
        String requestId = jsonObject.get("requestId").toString();
        String userObject = jsonObject.get("user").toString();

        User user = new Gson().fromJson(userObject, User.class);

        String action = jsonObject.get("action").toString();
        action = action.replace("\"", "");
        requestId = requestId.replace("\"", "");
        String response = "{}";

        switch (action) {
            case ALL_USERS:

                List<User> users = User.find();
                if (users.isEmpty()) {

                    response = String.format(RESPONSE_TEMPLATE, FAILED, "\"No users found\"", requestId, "[]");

                } else {
                    response = String.format(RESPONSE_TEMPLATE, SUCCESS, "\"Success\"", requestId, new Gson().toJson(users));
                }
                break;

            case SEARCH_USER:

                User foundUser = User.find(user.getNic());
                if (foundUser == null) {
                    response = String.format(RESPONSE_TEMPLATE, FAILED, "\"User not found\"", requestId, "[]");
                } else {
                    response = String.format(RESPONSE_TEMPLATE, SUCCESS, "\"Success\"", requestId, new Gson().toJson(foundUser));
                }
                break;

            case INSERT_USER:

                switch (user.save()) {
                    case 1:
                        response = String.format(RESPONSE_TEMPLATE, SUCCESS, "\"Success\"", requestId, new Gson().toJson(User.find(user.getNic())));
                        break;
                    case 2:
                        response = String.format(RESPONSE_TEMPLATE, BAD_REQUEST, "\"Missing parameters. Please send all parameters\"", requestId, "[]");
                        break;
                    case 4:
                        response = String.format(RESPONSE_TEMPLATE, CONFLICT, "\"Operation not allowed\"", requestId, "[]");
                        break;
                    default:
                        response = String.format(RESPONSE_TEMPLATE, FAILED, "\"Could not save the user\"", requestId, "[]");
                        break;
                }

                break;

            case UPDATE_USER:

                switch (user.update()) {
                    case 1:
                        response = String.format(RESPONSE_TEMPLATE, SUCCESS, "\"Success\"", requestId, new Gson().toJson(User.find(user.getNic())));
                        break;
                    case 2:
                        response = String.format(RESPONSE_TEMPLATE, BAD_REQUEST, "\"Missing parameters. Please send all parameters\"", requestId, "[]");
                        break;
                    case 4:
                        response = String.format(RESPONSE_TEMPLATE, CONFLICT, "\"Operation not allowed\"", requestId, "[]");
                        break;
                    default:
                        response = String.format(RESPONSE_TEMPLATE, FAILED, "\"Could not update the user\"", requestId, "[]");
                        break;
                }
                break;

            case DELETE_USER:

                switch (user.remove()) {
                    case 1:
                        response = String.format(RESPONSE_TEMPLATE, SUCCESS, "\"Success\"", requestId, new Gson().toJson(user));
                        break;
                    case 2:
                        response = String.format(RESPONSE_TEMPLATE, BAD_REQUEST, "\"Nic number not found! Please specify the user NIC\"", requestId, "[]");
                        break;
                    default:
                        response = String.format(RESPONSE_TEMPLATE, FAILED, "\"Could not delete the user\"", requestId, "[]");
                        break;
                }

                break;

            default:

                response = String.format(RESPONSE_TEMPLATE, METHOD_NOT_FOUND, "\"Invalid operation\"", requestId, "[]");

        }

        JsonElement responseElement = JsonParser.parseString(response);
        JsonObject responseObject = responseElement.getAsJsonObject();
        return new Gson().toJson(responseObject);

    }

}
