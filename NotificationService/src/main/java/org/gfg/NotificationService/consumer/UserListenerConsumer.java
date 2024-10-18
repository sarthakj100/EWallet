package org.gfg.NotificationService.consumer;

import org.example.CommonConstants;
import org.example.UserIdentifier;
import org.gfg.NotificationService.worker.EmailWorker;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class UserListenerConsumer {

    @Autowired
    EmailWorker worker;

    @KafkaListener(topics = CommonConstants.USER_TOPIC_NAME, groupId = "user-group")
    public void listenUserData(String msg){
        JSONObject jsonObject = new JSONObject(msg);

        String name = jsonObject.optString(CommonConstants.USER_NAME);
        String email = jsonObject.optString(CommonConstants.USER_EMAIL);

        UserIdentifier userIdentifier = jsonObject.optEnum(UserIdentifier.class,CommonConstants.USER_IDENTIFIER);

        worker.sendEmailToOnboardedUser(name,email,userIdentifier);


    }
}
