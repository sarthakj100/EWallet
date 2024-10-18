package org.gfg.TopUpService.service;

import org.example.CommonConstants;
import org.example.TxnStatus;
import org.gfg.TopUpService.model.Transaction;
import org.gfg.TopUpService.repository.TransactionRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TopUpService {


    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;


    @Autowired
    TransactionRepository transactionRepository;


    public String initiateTopUp(String sender, String amount){

        String txnId = UUID.randomUUID().toString();

        TxnStatus txnStatus = TxnStatus.INITIATED;

        Transaction transaction = Transaction.builder().txnId(txnId)
                .sender(sender).amount(Double.parseDouble(amount)).txnStatus(txnStatus).build()
                ;

        transactionRepository.save(transaction);

        System.out.println("Transaction saved with initiated status");

        // sending the data to kafka

        JSONObject txnObject = new JSONObject();
        txnObject.put(CommonConstants.SENDER, transaction.getSender());
        txnObject.put(CommonConstants.TXN_AMOUNT,transaction.getAmount());
        txnObject.put(CommonConstants.TXN_ID, transaction.getTxnId());

        kafkaTemplate.send(CommonConstants.TOPUP_TOPIC,txnObject.toString());

        System.out.println("Transaction data sent to kafka");

        return txnId;

    }


}
