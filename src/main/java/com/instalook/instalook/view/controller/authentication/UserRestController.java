package com.instalook.instalook.view.controller.authentication;

import com.instalook.instalook.view.controller.utils.BaseResponse;
import com.instalook.instalook.model.dal.entity.User;
import com.instalook.instalook.model.dal.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Amer Shaker
 */
@RestController
@RequestMapping("/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json")
    public ResponseEntity<BaseResponse> register(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        int id = userService.register(user);
        BaseResponse responseBody = new BaseResponse();

        if (id != 0) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setLocation(ucBuilder.path("/register/{id}")
                    .buildAndExpand(user.getUserId()).toUri());

            ResponseEntity<BaseResponse> response = new ResponseEntity<>(responseBody,
                    headers,
                    HttpStatus.CREATED);

            responseBody.setStatusCode(response.getStatusCode());
            responseBody.setStatusMessage("Successfully, registered.");
            return response;
        } else {
            ResponseEntity<BaseResponse> response = new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
            responseBody.setStatusCode(response.getStatusCode());
            responseBody.setStatusMessage("Duplicate entry for e-mail");
            return response;
        }
    }

    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            produces = "application/json")
    public Object login(@RequestParam("email") String email, @RequestParam("password") String password) {
        User user = userService.login(email, password);
        BaseResponse responseBody = new BaseResponse();

        if (user != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return user;
        } else {
            ResponseEntity<BaseResponse> response = new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
            responseBody.setStatusCode(response.getStatusCode());
            responseBody.setStatusMessage("Incorrect credentials");
            return response;
        }
    }

    @RequestMapping(value = "/userId",
            method = RequestMethod.GET,
            produces = "application/json")
    public Object getUserById(@RequestParam("userId") int userId) {
        User user = userService.getUserById(userId);
        BaseResponse responseBody = new BaseResponse();

        if (user != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return user;
        } else {
            ResponseEntity<BaseResponse> response = new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
            responseBody.setStatusCode(response.getStatusCode());
            responseBody.setStatusMessage("Incorrect credentials");
            return response;
        }
    }

    @RequestMapping(value = "/all",
            method = RequestMethod.GET,
            produces = "application/json")
    public Object getALlUsers() {
        List<User> users = userService.getALlUsers();
        BaseResponse responseBody = new BaseResponse();

        if (users != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return users;
        } else {
            ResponseEntity<BaseResponse> response = new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
            responseBody.setStatusCode(response.getStatusCode());
            responseBody.setStatusMessage("Incorrect credentials");
            return response;
        }
    }

    @RequestMapping(value = "/update",
            method = RequestMethod.PATCH,
            produces = "application/json")
    public Object updateUserData(@RequestBody User user) {
        boolean isSuccess = userService.updateUserData(user);
        BaseResponse responseBody = new BaseResponse();

        if (isSuccess) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            ResponseEntity<BaseResponse> response = new ResponseEntity<>(responseBody, HttpStatus.OK);
            responseBody.setStatusCode(response.getStatusCode());
            responseBody.setStatusMessage("Updated successfully");
            return response;
        } else {
            ResponseEntity<BaseResponse> response = new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
            responseBody.setStatusCode(response.getStatusCode());
            responseBody.setStatusMessage("This user doesn't exist");
            return response;
        }
    }

    @RequestMapping(value = "/delete",
            method = RequestMethod.DELETE,
            produces = "application/json")
    public Object deleteUserById(@RequestParam("userId") int userId) {
        boolean isSuccess = userService.deleteUserById(userId);
        BaseResponse responseBody = new BaseResponse();

        if (isSuccess) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            ResponseEntity<BaseResponse> response = new ResponseEntity<>(responseBody, HttpStatus.OK);
            responseBody.setStatusCode(response.getStatusCode());
            responseBody.setStatusMessage("Deleted successfully");
            return response;
        } else {
            ResponseEntity<BaseResponse> response = new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
            responseBody.setStatusCode(response.getStatusCode());
            responseBody.setStatusMessage("This user doesn't exist");
            return response;
        }
    }
}
