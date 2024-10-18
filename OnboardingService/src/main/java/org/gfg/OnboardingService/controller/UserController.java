package org.gfg.OnboardingService.controller;

import org.gfg.OnboardingService.model.User;
import org.gfg.OnboardingService.request.UserCreationRequest;
import org.gfg.OnboardingService.response.Response;
import org.gfg.OnboardingService.response.UserCreationResponse;
import org.gfg.OnboardingService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-service")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/create/user")
    public ResponseEntity<Response> onboardUser(@RequestBody  UserCreationRequest userCreationRequest){

        if (userCreationRequest==null){
            Response response = new Response();
            response.setCode("022");
            response.setMessage("Please provide the user information");

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (userCreationRequest.getEmail().length()==0 || userCreationRequest.getMobileNo().length()==0){
            Response response = new Response();
            response.setCode("023");
            response.setMessage("Please provide the correct email or mobile");

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        User user = userService.createUserInDatabase(userCreationRequest);
        UserCreationResponse userCreationResponse = new UserCreationResponse();
        if (user==null){
            userCreationResponse.setCode("01");
            userCreationResponse.setMessage("User Not Onboarded");
            return new ResponseEntity<>(userCreationResponse,HttpStatus.BAD_REQUEST);
        }else {
            userCreationResponse.setCode("01");
            userCreationResponse.setMessage("User Onboarded successfully");
            userCreationResponse.setName(user.getName());
            userCreationResponse.setEmail(user.getEmail());
            return new ResponseEntity<>(userCreationResponse,HttpStatus.CREATED);
        }


    }


    @GetMapping("/validate/user")
    public String validateUser(@RequestParam("mobileNo") String mobileNo){
        System.out.println(mobileNo);
        return userService.validateUser(mobileNo);
    }
}
