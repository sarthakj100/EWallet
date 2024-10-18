package org.gfg.OnboardingService.service;

import org.apache.kafka.common.protocol.types.Field;
import org.example.CommonConstants;
import org.gfg.OnboardingService.model.User;
import org.gfg.OnboardingService.model.enums.UserStatus;
import org.gfg.OnboardingService.model.enums.UserType;
import org.gfg.OnboardingService.repository.UserRepository;
import org.gfg.OnboardingService.request.UserCreationRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;


    public User createUserInDatabase(UserCreationRequest userCreationRequest){

        User user = userCreationRequest.toUser();
        String password = userCreationRequest.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setUserType(UserType.NORMAL);
        user.setUserStatus(UserStatus.ACTIVE);

        try {
            userRepository.save(user);
        }
        catch (Exception exception){
            System.out.println("Exception in User: "+exception);
            return null;
        }


        // if the user is successfully onboarded, send the data to kafka

        JSONObject userDetails = new JSONObject();
        userDetails.put(CommonConstants.USER_NAME,user.getName());
        userDetails.put(CommonConstants.USER_EMAIL,user.getEmail());
        userDetails.put(CommonConstants.USER_MOBILE,user.getMobileNo());
        userDetails.put(CommonConstants.USER_IDENTIFIER,user.getUserIdentifier());
        userDetails.put(CommonConstants.USER_IDENTIFIER_VALUE,user.getUserIdentifierValue());

        kafkaTemplate.send(CommonConstants.USER_TOPIC_NAME,userDetails.toString());

        System.out.println("Data send to kakfa");
        return user;
    }


    public String validateUser(String mobile){
        User user = userRepository.findByMobileNo(mobile);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CommonConstants.USER_MOBILE,user.getMobileNo());
        jsonObject.put(CommonConstants.USER_EMAIL,user.getEmail());
        jsonObject.put(CommonConstants.USER_PASSWORD,user.getPassword());

        return jsonObject.toString();
    }
}
