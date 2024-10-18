package org.gfg.WalletService.consumer;

import org.example.CommonConstants;
import org.gfg.WalletService.service.WalletService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class TransactionalConsumer {

    @Autowired
    WalletService walletService;
//TOPUP_TOPIC

    @KafkaListener(topics = CommonConstants.TRANSACTIONAL_TOPIC,groupId = "transaction-group")
    public void listenNewTransaction(String msg){
        System.out.println(msg);

        JSONObject jsonObject = new JSONObject(msg);

        String sender = jsonObject.getString(CommonConstants.SENDER);
        String receiver = jsonObject.getString(CommonConstants.RECEIVER);
        double amount = jsonObject.getDouble(CommonConstants.TXN_AMOUNT);
        String txnId = jsonObject.getString(CommonConstants.TXN_ID);

        walletService.processTransaction(sender,receiver,Double.toString(amount),txnId);



        }
    }

