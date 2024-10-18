package org.gfg.WalletService.consumer;

import org.example.CommonConstants;
import org.example.UserIdentifier;
import org.gfg.WalletService.Repository.WalletRepository;
import org.gfg.WalletService.model.Wallet;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class UserOnboardedConsumer {

    @Value("${wallet.initial.amount}")
    String amount;

    @Autowired
    WalletRepository walletRepository;


    @KafkaListener(topics = CommonConstants.USER_TOPIC_NAME, groupId = "wallet-group")
    public void listenNewCreatedUser(String msg){
        System.out.println(msg);

        JSONObject jsonObject =new JSONObject(msg);

        String name = jsonObject.optString(CommonConstants.USER_NAME);
        String email = jsonObject.optString(CommonConstants.USER_EMAIL);
        String mobileNo = jsonObject.optString(CommonConstants.USER_MOBILE);

        UserIdentifier userIdentifier = jsonObject.optEnum(UserIdentifier.class,CommonConstants.USER_IDENTIFIER);
        String userIdentifierValue = jsonObject.optString(CommonConstants.USER_IDENTIFIER_VALUE);

        Wallet wallet = Wallet.builder().email(email).mobileNo(mobileNo).balance(Double.parseDouble(amount))
                .userIdentifier(userIdentifier).userIdentifierValue(userIdentifierValue).build();

        walletRepository.save(wallet);

        System.out.println("Wallet account has been created successfully");

    }
}
