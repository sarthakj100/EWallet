package org.gfg.WalletService.consumer;


import org.example.CommonConstants;
import org.gfg.WalletService.service.WalletService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class TopUpConsumer {

    @Autowired
    WalletService walletService;

    @KafkaListener(topics = CommonConstants.TOPUP_TOPIC, groupId = "topup-group")
    public void listenNewTopUp(String msg){
        System.out.println(msg);

        JSONObject jsonObject = new JSONObject(msg);

        String sender = jsonObject.getString(CommonConstants.SENDER);
        double amount = jsonObject.getDouble(CommonConstants.TXN_AMOUNT);
        String txnId = jsonObject.getString(CommonConstants.TXN_ID);

        walletService.processTopUP(sender,Double.toString(amount),txnId);
    }
}
